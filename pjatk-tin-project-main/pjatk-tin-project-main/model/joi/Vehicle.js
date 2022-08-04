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
            case "any.required":
                err.message = "Pole jest wymagane";
                break;
            case "string.min":
                err.message = `Pole powinno zawierać co najmniej ${err.local.limit} znaki`;
                if(err.path[0] !== 'registration')
                    err.message += ' lub pozostać puste'
                break;
            case "string.max":
                err.message = `Pole powinno zawierać co najwyżej ${err.local.limit} znaków`;
                if(err.path[0] !== 'registration')
                    err.message += ' lub pozostać puste'
                break;
            default:
                console.log(err.code);
                break;
        }
    });
    return errors;
}

const vehSchema = Joi.object({
    id: Joi.string()
        .optional()
        .allow(""),
    registration: Joi.string()
        .min(2)
        .max(8)
        .required()
        .regex(specialCharacters, { invert: true })
        .error(errMessages),
    type: Joi.string()
        .optional()
        .allow("")
        .min(2)
        .max(30)
        .regex(specialCharacters, { invert: true })
        .error(errMessages),
    color: Joi.string()
        .optional()
        .allow("")
        .min(2)
        .max(30)
        .regex(specialCharacters, { invert: true })
        .error(errMessages),
});


module.exports = vehSchema;