const VehicleRepository = require('../repository/mysql2/VehicleRepository');

exports.showVehicleList = (req, res, next) => {
    VehicleRepository.getVehicles()
        .then(vehs => {
            res.render('pages/vehicle/vehicle-list', {
                vehs: vehs,
                navLocation: 'vehicle'
            });
        });
}

exports.showAddVehicleForm = (req, res, next) => {
    res.render('pages/vehicle/vehicle-form', {
        veh: {},
        pageTitle: 'Dodaj nowy pojazd',
        formMode: 'createNew',
        btnLabel: 'Dodaj',
        formAction: '/vehicle/add',
        navLocation: 'vehicle',
        validationErrors: []
    });
}

exports.showEditVehicleForm = (req, res, next) => {
    console.log("show edit veh form from veh controller");
    const registration = req.params.registration;
    const validationErrors = [];
    let encounters;
    VehicleRepository.getVehicleEncounters(registration)
        .then(encs => {
            encounters = encs;
            return VehicleRepository.getVehicleById(registration)
        })
        .then(veh => {
            res.render('pages/vehicle/vehicle-form', {
                veh: veh,
                registration: registration,
                encounters: encounters,
                formMode: 'edit',
                pageTitle: 'Edycja danych pojazdu',
                btnLabel: 'Edytuj dane pojazdu',
                formAction: '/vehicle/edit',
                navLocation: 'vehicle',
                validationErrors: validationErrors
            });
        });
};

exports.showVehicleDetails = (req, res, next) => {
    const registration = req.params.registration;
    const validationErrors = [];
    let encounters;
    VehicleRepository.getVehicleEncounters(registration)
        .then(encs => {
            encounters = encs;
            return VehicleRepository.getVehicleById(registration)
        })
        .then(veh => {
            res.render('pages/vehicle/vehicle-form', {
                veh: veh,
                encounters: encounters,
                formMode: 'showDetails',
                pageTitle: 'Szczegóły pojazdu',
                formAction: '',
                navLocation: 'vehicle',
                validationErrors: validationErrors
            });
        });
}

exports.addVehicle = (req, res, next) => {
    console.log("add vehicle from veh controller");
    const vehData = {...req.body};
    let errorMessage;

    VehicleRepository.createVehicle(vehData)
        .then(result => {
            res.redirect('/vehicle');
        })
        .catch(err => {
            console.log('error returned:\n' + err.message);
            errorMessage = err.message
            if(err.details === undefined){
                if(err.message.startsWith('Duplicate entry'))
                    errorMessage = 'Pojazd o podanej tablicy rejestracyjnej już istnieje! (' + err.message + ')';
                res.render('pages/vehicle/vehicle-error',{
                    pageTitle: 'Wystąpił błąd',
                    errorMessage: errorMessage,
                    navLocation: 'vehicle'
                })
            }
            res.render('pages/vehicle/vehicle-form', {
                veh: vehData,
                pageTitle: 'Dodawanie Pojazdu',
                formMode: 'createNew',
                btnLabel: 'Dodaj pojazd',
                formAction: '/vehicle/add',
                navLocation: 'vehicle',
                validationErrors: err.details
            });
        });
};

exports.updateVehicle = (req, res, next) => {
    let error;
    console.log(req.body.registration);
    const id = req.body.id;
    const registration = id;
    let vehData = {...req.body};
    vehData.registration = registration;

    VehicleRepository.updateVehicle(id, vehData)
        .then(result => {
            res.redirect('/vehicle');
        })
        .catch(err => {
            error = err;
            console.log(err.details);
            return VehicleRepository.getVehicleEncounters(id)
                .then(encs => {
                    res.render('pages/vehicle/vehicle-form', {
                        veh: vehData,
                        registration: registration,
                        encounters: encs,
                        formMode: 'edit',
                        pageTitle: 'Edycja danych pojazdu',
                        btnLabel: 'Edytuj dane pojazdu',
                        formAction: '/vehicle/edit',
                        navLocation: 'vehicle',
                        validationErrors: error.details
                    });
                });
        })
};

exports.deleteVehicle = (req, res, next) => {
    const registration = req.params.registration;
    let errorMessage;
    VehicleRepository.deleteVehicle(registration)
        .then(() => {
            res.redirect('/vehicle');
        })
        .catch(err => {
            errorMessage = err.message;
            res.render('pages/vehicle/vehicle-error', {
                errorMessage: errorMessage,
                navLocation: 'vehicle'
            });
        });
};






