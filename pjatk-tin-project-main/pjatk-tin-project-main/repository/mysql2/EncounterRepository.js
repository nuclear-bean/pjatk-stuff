const db = require('../../config/mysql2/db');
const encSchema = require('../../model/joi/Encounter');


exports.getEncounters = () => {
    return db.promise().query('SELECT * FROM Encounter')
        .then( (results, fields) => {
            return results[0];
        })
        .catch(err => {
            console.log(err);
            throw err;
        });
};

exports.getEncounterById = (encId) => {
    console.log("Encounter Repo. get by ID: " + encId);
    const query = `SELECT *
    FROM Encounter  
    where id = ?`
    return db.promise().query(query, [encId])
        .then( (results, fields) => {
            const firstRow = results[0][0];
            if(!firstRow) {
                console.log("not a first row");
                return {};
            }
            // noinspection UnnecessaryLocalVariableJS
            const enc = {
                id: parseInt(encId),
                Car_registration: firstRow.Car_registration,
                Camera_id: firstRow.Camera_id,
                time: firstRow.time,
                authorized: firstRow.authorized,
                direction: firstRow.direction
            }
            console.log("query OK");
            return enc;
        })
        .catch(err => {
            console.log(err);
            throw err;
        });
};

exports.createEncounter = (newEncData) => {
    const vRes = encSchema.validate(newEncData, { abortEarly: false} );
    if(vRes.error) {
        console.log("error returned " + vRes.error);
        return Promise.reject(vRes.error);
    }
    console.log('createEncounter');
    const sql = 'INSERT into Encounter (Car_registration,Camera_id,time,authorized,direction) VALUES (?,?,FROM_UNIXTIME(?),?,?)';
    return db.promise().execute(sql, [newEncData.Car_registration, newEncData.Camera_id, newEncData.time,newEncData.authorized,newEncData.direction]);
};

exports.updateEncounter = (encId, encData) => {
    console.log('DATA IN UDATE ENC/REPO\n');
    console.log('-----------')
    console.log('update enc repo - enc:');
    for (let xd in encData){
        console.log(xd + ' ' + encData[xd])
    }
    console.log('-----------')
    const vRes = encSchema.validate(encData, { abortEarly: false} );
    if(vRes.error) {
        console.log("error returned " + vRes.error);
        return Promise.reject(vRes.error);
    }
    const sql = `UPDATE Encounter set time = FROM_UNIXTIME(?), authorized = ?, direction = ? WHERE id = ?`;
    return db.promise().execute(sql, [encData.time, encData.authorized, encData.direction, encId]);
};

exports.deleteEncounter = (encId) => {
    const sql = 'DELETE FROM Encounter WHERE id = ?'
    return db.promise().execute(sql, [encId]);
};

