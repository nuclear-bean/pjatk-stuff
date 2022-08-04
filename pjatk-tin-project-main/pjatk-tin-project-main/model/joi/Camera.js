const Joi = require('joi');

const specialCharacters = new RegExp(/^.*[@#?`!';<>{}$].*$/s);

const errMessages = (errors) => {
    errors.forEach(err => {
        switch (err.code) {
            case "string.empty":
                err.message = "Pole jest wymagane";
                break;
            case "string.pattern.invert.base":
                err.message = 'Pole zawiera niedozwolone znaki!';
                break;
            case "string.min":
                err.message = `Pole powinno zawierać co najmniej ${err.local.limit} znaki`;
                if(err.path[0] !== 'alias')
                    err.message += ' lub pozostać puste'
                break;
            case "string.max":
                err.message = `Pole powinno zawierać co najwyżej ${err.local.limit} znaków`;
                if(err.path[0] !== 'alias')
                    err.message += ' lub pozostać puste'
                break;
            default:
                err.message = `To pole zawiera błędy`;
                break;
        }
    });
    return errors;
}

const camSchema = Joi.object({
    id: Joi.number()
        .optional()
        .allow(""),
    alias: Joi.string()
        .min(2)
        .max(30)
        .required()
        .regex(specialCharacters, { invert: true })
        .error(errMessages),
    location: Joi.string()
        .optional()
        .allow("")
        .min(2)
        .max(30)
        .regex(specialCharacters, { invert: true })
        .error(errMessages),
    manufacturer: Joi.string()
        .optional()
        .allow("")
        .min(2)
        .max(30)
        .regex(specialCharacters, { invert: true })
        .error(errMessages),
    resolution: Joi.string()
        .optional()
        .allow("")
        .min(2)
        .max(30)
        .regex(specialCharacters, { invert: true })
        .error(errMessages)
});



module.exports = camSchema;