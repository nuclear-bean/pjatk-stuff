const EncounterRepository = require('../repository/mysql2/EncounterRepository');

const CameraRepository = require('../repository/mysql2/CameraRepository');
const VehicleRepository = require('../repository/mysql2/VehicleRepository');


exports.showEncounterList = (req, res, next) => {
    EncounterRepository.getEncounters()
        .then(encs => {
            res.render('pages/encounter/encounter-list', {
                encs: encs,
                navLocation: 'encounter'
            });
        });
}

exports.showAddEncounterForm = (req, res, next) => {
    let allCams, allVehs;
    const enc = {
        id: -1,
        Camera_id: -2,
        Car_registration: -3,
        authorized: 0,
        direction: 1
    }
    CameraRepository.getCameras()
        .then(cams => {
            allCams = cams;
            return VehicleRepository.getVehicles();
        })
        .then(vehs => {
            allVehs = vehs;
            res.render('pages/encounter/encounter-form', {
                allCams: allCams,
                allVehs: allVehs,
                enc: enc,
                pageTitle: 'Nowy wpis',
                formMode: 'createNew',
                btnLabel: 'Dodaj wpis',
                formAction: '/encounter/add',
                navLocation: 'encounter',
                validationErrors: []
            });
        });
}

exports.showEditEncounterForm = (req, res, next) => {
    let allCams, allVehs;
    const encId = req.params.encId;
    CameraRepository.getCameras()
        .then(cams => {
            allCams = cams;
            return VehicleRepository.getVehicles();
        })
        .then(vehs => {
            allVehs = vehs;
            return EncounterRepository.getEncounterById(encId)
        })
        .then(enc => {
            console.log('xdxd')
            res.render('pages/encounter/encounter-form', {
                enc: enc,
                allCams: allCams,
                allVehs: allVehs,
                formMode: 'edit',
                pageTitle: 'Edycja danych wpisu',
                btnLabel: 'Edytuj dane wpisu',
                formAction: '/encounter/edit',
                navLocation: 'encounter',
                validationErrors: []
            });
        });
};

exports.showEncounterDetails = (req, res, next) => {
    const encId = req.params.encId;
    let allCams, allVehs;
    console.log("Enc Controller, show encounter details for enc id: " + encId);
    CameraRepository.getCameras()
        .then(cams => {
            allCams = cams;
            return VehicleRepository.getVehicles();
        })
        .then(vehs => {
            allVehs = vehs;
            return EncounterRepository.getEncounterById(encId)
        })
        .then(enc => {
            res.render('pages/encounter/encounter-form', {
                enc: enc,
                allCams: allCams,
                allVehs: allVehs,
                formMode: 'showDetails',
                pageTitle: 'Szczegóły wpisu',
                formAction: '',
                navLocation: 'encounter',
                validationErrors: []
            });
        });
}

exports.addEncounter = (req, res, next) => {
    const encData = { ...req.body };
    console.log("add encounter data BELOW\n" + encData);

    const originalTimestamp = req.body.time;

    let ts = (new Date(req.body.time).getTime()/1000);
    ts += 3600;
    console.log('form ts: ' + originalTimestamp)
    console.log('epoch: ' + ts);
    encData['time'] = ts;
    //todo 2019-01-01 12:10: -> nie ma prawa przejść xd


    //TODO CZAS COFA SIE O 1 H

    EncounterRepository.createEncounter(encData)
        .then( result => {
            res.redirect('/encounter');
        })
        .catch(err => {
            let allCams, allVehs;
            const error = err;

            encData['time'] = originalTimestamp;

            if(encData['Car_registration'] === undefined)
                encData['Car_registration'] = 'xxx'

            if(encData['Camera_id'] === undefined)
                encData['Camera_id'] = -1

            for ( let xd in encData){
                let name = xd;
                let val = encData[name];
                console.log(name + ' ' + val)
            }


            CameraRepository.getCameras()
                .then(cams => {
                    allCams = cams;
                    return VehicleRepository.getVehicles();
                })
                .then(vehs => {
                    allVehs = vehs;
                    res.render('pages/encounter/encounter-form', {
                        allCams: allCams,
                        allVehs: allVehs,
                        enc: encData,
                        pageTitle: 'Nowy wpis',
                        formMode: 'createNew',
                        btnLabel: 'Dodaj wpis',
                        formAction: '/encounter/add',
                        navLocation: 'encounter',
                        validationErrors: error.details
                    });
                });
        });
};

exports.updateEncounter = (req, res, next) => {
    const encId = req.body.id;
    const originalTimestamp = req.body.time;
    let encData = { ...req.body };
    encData.Camera_id = 0;
    encData.Car_registration='xxx'
    console.log('---------')
    encData['time'] = (new Date(req.body.time).getTime()/1000);
    encData['time'] += 3600;

    EncounterRepository.updateEncounter(encId, encData)
        .then( result => {
            res.redirect('/encounter');
        })
        .catch(err => {
            let allVehs,allCams;
            let error = err;
            encData['time'] = originalTimestamp;
            VehicleRepository.getVehicles()
                .then(vehs => {
                    allVehs = vehs;
                    return CameraRepository.getCameras()
                })
                .then(cams => {
                    allCams = cams;
                    return EncounterRepository.getEncounterById(encData['id'])
                })
                .then(enc => {
                    console.log('im here')

                    res.render('pages/encounter/encounter-form', {
                        allCams: allCams,
                        allVehs: allVehs,
                        enc: enc,
                        pageTitle: 'Edycja danych wpisu',
                        formMode: 'edit',
                        btnLabel: 'Edytuj dane',
                        formAction: '/encounter/edit',
                        navLocation: 'encounter',
                        validationErrors: error.details
                    });
                })
        });
};

exports.deleteEncounter = (req, res, next) => {
    console.log("delete encounter");
    const encId = req.params.encId;
    EncounterRepository.deleteEncounter(encId)
        .then( () => {
            res.redirect('/encounter');
        });
};






