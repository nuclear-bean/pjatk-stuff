const db = require('../../config/mysql2/db');
const vehSchema = require('../../model/joi/Vehicle');

exports.getVehicles = () => {
    return db.promise().query('SELECT * FROM Vehicle')
        .then( (results, fields) => {
            console.log(results[0]);
            return results[0];
        })
        .catch(err => {
            console.log(err);
            throw err;
        });
};

exports.getVehicleEncounters = (registration) => {
    console.log('getting encounters for reg ' + registration);
    const query = `SELECT * FROM Encounter WHERE Car_registration = ?`;
    return db.promise().query(query, [registration])
        .then( (results, fields) => {
            console.log(results[0]);
            return results[0];
        })
        .catch(err => {
            console.log(err);
            throw err;
        });
}

exports.getVehicleById = (registration) => {
    console.log(registration);
    const query = `SELECT *
    FROM Vehicle  
    WHERE registration = ?`
    return db.promise().query(query, [registration])
        .then( (results, fields) => {
            const firstRow = results[0][0];
            if(!firstRow) {
                return {};
            }
            // noinspection UnnecessaryLocalVariableJS
            const veh = {
                registration: firstRow.registration,
                type: firstRow.type,
                color: firstRow.color
            }
            return veh;
        })
        .catch(err => {
            console.log(err);
            throw err;
        });
};

exports.createVehicle = (newVehData) => {
    const vRes = vehSchema.validate(newVehData, { abortEarly: false} );
    if(vRes.error) {
        console.log("error returned " + vRes.error);
        return Promise.reject(vRes.error);
    }
    const registration = newVehData.registration;
    const type = newVehData.type;
    const color = newVehData.color;
    const sql = 'INSERT into Vehicle (registration, type, color) VALUES (?, ?, ?)'
    return db.promise().execute(sql, [registration, type, color]);


};

exports.updateVehicle = (registration, vehData) => {
    console.log("veh repo veh data\n" + vehData.properties);
    const vRes = vehSchema.validate(vehData, { abortEarly: false} );
    if(vRes.error) {
        console.log("error returned " + vRes.error);
        return Promise.reject(vRes.error);
    }
    const sql = `UPDATE Vehicle set type = ?, color = ? where registration = ?`;
    return db.promise().execute(sql, [vehData.type, vehData.color,vehData.registration]);
};

exports.deleteVehicle = (registration) => {
    const sql = 'DELETE FROM Vehicle WHERE registration = ?'
    return db.promise().execute(sql, [registration]);
};

