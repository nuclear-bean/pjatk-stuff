const express = require('express');
const router = express.Router();

const vehicleController = require('../controllers/vehicleController');

router.get('/', vehicleController.showVehicleList);
router.get('/add', vehicleController.showAddVehicleForm);
router.get('/edit/:registration', vehicleController.showEditVehicleForm);
router.get('/details/:registration', vehicleController.showVehicleDetails);

// dane z formularzy
router.post('/add', vehicleController.addVehicle);
router.post('/edit', vehicleController.updateVehicle);
router.get('/delete/:registration', vehicleController.deleteVehicle);

module.exports = router;