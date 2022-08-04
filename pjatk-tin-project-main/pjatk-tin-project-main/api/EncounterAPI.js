const EncounterRepository = require('../repository/mysql2/EncounterRepository');

exports.getEncounters = (req, res, next) => {
    EncounterRepository.getEncounters()
        .then(encounters => {
            res.status(200).json(encounters);
        })
        .catch(err => {
            console.log(err);
        });
};


exports.getEncounterById = (req, res, next) => {
    const encId = req.params.encId;
    EncounterRepository.getEncounterById(encId)
        .then(veh => {
            if(!veh) {
                res.status(404).json({
                    message: 'Encounter with ID: ' + encId + ' not found'
                })
            } else {
                res.status(200).json(veh);
            }
        });
};

exports.createEncounter = (req, res, next) => {
    EncounterRepository.createEncounter(req.body)
        .then(newObj => {
            res.status(201).json(newObj);
        })
        .catch(err => {
            console.log(err);
            if (!err.statusCode) {
                err.statusCode = 500;
            }
            next(err);
        });
};


exports.updateEncounter = (req, res, next) => {
    const encId = req.params.encId;
    console.log(req.body)
    EncounterRepository.updateEncounter(encId, req.body)
        .then(result => {
            res.status(200).json({message: 'Encounter updated!', cam: result});
        })
        .catch(err => {
            if (!err.statusCode) {
                err.statusCode = 500;
            }
            next(err);
        });
};


exports.deleteEncounter = (req, res, next) => {
    const encId = req.params.encId;
    EncounterRepository.deleteEncounter(encId)
        .then(result => {
            res.status(200).json({message: 'Removed Encounter', cam: result});
        })
        .catch(err => {
            if (!err.statusCode) {
                err.statusCode = 500;
            }
            next(err);
        });
};
