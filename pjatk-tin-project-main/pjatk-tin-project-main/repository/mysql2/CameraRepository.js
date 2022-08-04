const db = require('../../config/mysql2/db');
const camSchema = require('../../model/joi/Camera');

exports.getCameras = () => {
    return db.promise().query('SELECT * FROM Camera')
        .then( (results, fields) => {
            return results[0];
        })
        .catch(err => {
            console.log(err);
            throw err;
        });
};

exports.getCameraById = (camId) => {
    const query = `SELECT c.id as id, c.alias, c.location, c.manufacturer, c.resolution
    FROM Camera c  
    where c.id = ?`
    return db.promise().query(query, [camId])
        .then( (results, fields) => {
            const firstRow = results[0][0];
            if(!firstRow) {
                return {};
            }
            // noinspection UnnecessaryLocalVariableJS
            const cam = {
                id: parseInt(camId),
                alias: firstRow.alias,
                location: firstRow.location,
                manufacturer: firstRow.manufacturer,
                resolution: firstRow.resolution
            }

            return cam;
        })
        .catch(err => {
            console.log(err);
            throw err;
        });
};

exports.getCameraEncounters = (camId) => {
    console.log('getting encounters for camId ' + camId);
    const query = `SELECT * FROM Encounter WHERE Camera_id = ?`;
    return db.promise().query(query, [camId])
        .then( (results, fields) => {
            return results[0];
        })
        .catch(err => {
            console.log(err);
            throw err;
        });
}


exports.createCamera = (newCamData) => {
    const vRes = camSchema.validate(newCamData, { abortEarly: false} );
    if(vRes.error) {
        console.log("error returned " + vRes.error);
        return Promise.reject(vRes.error);
    }
    const alias = newCamData.alias;
    const location = newCamData.location;
    const manufacturer = newCamData.manufacturer;
    const resolution = newCamData.resolution;
    const sql = 'INSERT into Camera (alias, location, manufacturer, resolution) VALUES (?, ?, ?, ?)'
    return db.promise().execute(sql, [alias, location, manufacturer, resolution]);
};

exports.updateCamera = (camId, camData) => {
    const vRes = camSchema.validate(camData, { abortEarly: false} );
    if(vRes.error) {
        console.log("error returned " + vRes.error);
        return Promise.reject(vRes.error);
    }
    const sql = `UPDATE Camera set id = ?, alias = ?, location = ?, manufacturer = ?, resolution = ? where id = ?`;
    return db.promise().execute(sql, [camData.id, camData.alias, camData.location, camData.manufacturer, camData.resolution, camId]);
};

exports.deleteCamera = (camId) => {
    const sql = 'DELETE FROM Camera WHERE id = ?'
    return db.promise().execute(sql, [camId]);
};

