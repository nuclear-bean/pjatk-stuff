const express = require('express');
const router = express.Router();

const encounterController = require('../controllers/encounterController');

router.get('/', encounterController.showEncounterList);
router.get('/add', encounterController.showAddEncounterForm);
router.get('/edit/:encId', encounterController.showEditEncounterForm);
router.get('/details/:encId', encounterController.showEncounterDetails);



// dane z formularzy
router.post('/add', encounterController.addEncounter);
router.post('/edit', encounterController.updateEncounter);
router.get('/delete/:encId', encounterController.deleteEncounter);

module.exports = router;