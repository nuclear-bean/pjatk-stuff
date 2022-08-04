const Joi = require('joi');

const specialCharacters = new RegExp(/^.*[#?`';<>{}$].*$/s);

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
                break;
            case "string.max":
                err.message = `Pole powinno zawierać co najwyżej ${err.local.limit} znaki`;
                break;
            case "date.format":
                err.message = "Niepoprawny format pola"
                break;
            case "date.base":
                err.message = "Pole powinno mieć format yyyy-MM-dd hh:mm:ss"
                break;
            case "date.less":
                err.message = "Data nie może być z przyszłości";
                break;
            case "date.greater":
                err.message = "Data nie może być mniejsza niż 1970-01-01 00:00:01"
                break
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
    Car_registration: Joi.string()
        .min(2)
        .max(10)
        .required()
        .error(errMessages),
    Camera_id: Joi.number()
        .required()
        .error(errMessages),
    time: Joi.date().timestamp('unix')
        .required()
        .less("now")
        .greater(0)
        .error(errMessages),
    authorized: Joi.string()
        .optional()
        .allow("")
        .min(1)
        .max(1)
        .error(errMessages),
    direction: Joi.string()
        .optional()
        .allow("")
        .min(1)
        .max(1)
        .error(errMessages),
});


module.exports = vehSchema;