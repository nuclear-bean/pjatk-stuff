const express = require('express');
const router = express.Router();

const vehicleApiController = require('../../api/VehicleAPI');

router.get('/', vehicleApiController.getVehicles);
router.get('/:registration', vehicleApiController.getVehicleById);
router.post('/', vehicleApiController.createVehicle);
router.put('/:registration', vehicleApiController.updateVehicle);
router.delete('/:registration', vehicleApiController.deleteVehicle);

module.exports = router;