const VehicleRepository = require('../repository/mysql2/VehicleRepository');

exports.getVehicles = (req, res, next) => {
    VehicleRepository.getVehicles()
        .then(vehicles => {
            res.status(200).json(vehicles);
        })
        .catch(err => {
            console.log(err);
        });
};


exports.getVehicleById = (req, res, next) => {
    const registration = req.params.registration;
    VehicleRepository.getVehicleById(registration)
        .then(veh => {
            if(!veh) {
                res.status(404).json({
                    message: 'Vehicle with registration: ' + registration + ' not found'
                })
            } else {
                res.status(200).json(veh);
            }
        });
};

exports.createVehicle = (req, res, next) => {
    console.log(req.body);
    VehicleRepository.createVehicle(req.body)
        .then(newObj => {
            res.status(201).json(newObj);
        })
        .catch(err => {
            if (!err.statusCode) {
                err.statusCode = 500;
            }
            next(err);
        });
};


exports.updateVehicle = (req, res, next) => {
    const registration = req.params.registration;
    console.log(req.body)
    VehicleRepository.updateVehicle(registration, req.body)
        .then(result => {
            res.status(200).json({message: 'Vehicle updated!', cam: result});
        })
        .catch(err => {
            if (!err.statusCode) {
                err.statusCode = 500;
            }
            next(err);
        });
};


exports.deleteVehicle = (req, res, next) => {
    const registration = req.params.registration;
    VehicleRepository.deleteVehicle(registration)
        .then(result => {
            res.status(200).json({message: 'Removed Vehicle', cam: result});
        })
        .catch(err => {
            if (!err.statusCode) {
                err.statusCode = 500;
            }
            next(err);
        });
};
