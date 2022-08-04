const express = require('express');
const router = express.Router();

const encounterApiController = require('../../api/EncounterAPI');

router.get('/', encounterApiController.getEncounters);
router.get('/:encId', encounterApiController.getEncounterById);
router.post('/', encounterApiController.createEncounter);
router.put('/:encId', encounterApiController.updateEncounter);
router.delete('/:encId', encounterApiController.deleteEncounter);

module.exports = router;