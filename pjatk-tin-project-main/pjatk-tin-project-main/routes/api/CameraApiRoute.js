const express = require('express');
const router = express.Router();

const camApiController = require('../../api/CameraAPI');

router.get('/', camApiController.getCameras);
router.get('/:camId', camApiController.getCameraById);
router.post('/', camApiController.createCamera);
router.put('/:camId', camApiController.updateCamera);
router.delete('/:camId', camApiController.deleteCamera);

module.exports = router;