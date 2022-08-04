const CameraRepository = require('../repository/mysql2/CameraRepository');

exports.getCameras = (req, res, next) => {
    CameraRepository.getCameras()
        .then(cameras => {
            res.status(200).json(cameras);
        })
        .catch(err => {
            console.log(err);
        });
};


exports.getCameraById = (req, res, next) => {
    const camId = req.params.camId;
    CameraRepository.getCameraById(camId)
        .then(cam => {
            if(!cam) {
                res.status(404).json({
                    message: 'Camera with id: ' + camId + ' not found'
                })
            } else {
                res.status(200).json(cam);
            }
        });
};

exports.createCamera = (req, res, next) => {
    console.log(req.body);
    CameraRepository.createCamera(req.body)
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


exports.updateCamera = (req, res, next) => {
    const camId = req.params.camId;
    console.log(req.body)
    CameraRepository.updateCamera(camId, req.body)
        .then(result => {
            res.status(200).json({message: 'Camera updated!', cam: result});
        })
        .catch(err => {
            if (!err.statusCode) {
                err.statusCode = 500;
            }
            next(err);
        });
};


exports.deleteCamera = (req, res, next) => {
    const camId = req.params.camId;
    CameraRepository.deleteCamera(camId)
        .then(result => {
            res.status(200).json({message: 'Removed Camera', cam: result});
        })
        .catch(err => {
            if (!err.statusCode) {
                err.statusCode = 500;
            }
            next(err);
        });
};
