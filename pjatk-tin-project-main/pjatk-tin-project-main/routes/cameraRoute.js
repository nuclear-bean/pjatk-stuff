const express = require('express');
const router = express.Router();

const cameraController = require('../controllers/cameraController');


router.get('/', cameraController.showCameraList);
router.get('/add', cameraController.showAddCameraForm);
router.get('/edit/:camId', cameraController.showEditCameraForm);
router.get('/details/:camId', cameraController.showCameraDetails);

// dane z formularzy
router.post('/add', cameraController.addCamera);
router.post('/edit', cameraController.updateCamera);
router.get('/delete/:camId', cameraController.deleteCamera);


module.exports = router;