function validateForm() {
    const registration = document.getElementById('registration');
    const type = document.getElementById('type');
    const color = document.getElementById('color');

    const errorRegistration = document.getElementById('errorRegistration');
    const errorType = document.getElementById('errorType');
    const errorColor = document.getElementById('errorColor');

    const errorsSummary = document.getElementById('errorsSummary');

    let valid = true;

    console.log("test from veh vad");

    // RESET ERRORS
    resetErrors([registration, type, color], [errorRegistration, errorType, errorColor], errorsSummary);

    // CHECK REGISTRATION
    if (!checkRequired(registration.value)) {
        valid = false;
        registration.classList.add("error-input");
        errorRegistration.innerText = "Pole jest wymagane";
    } else if (!checkTextLengthRange(registration.value, 2, 8)) {
        valid = false;
        registration.classList.add("error-input");
        errorRegistration.innerText = "Pole powinno zawierać od 2 do 8 znaków";
    } else if (registration.value.length > 0 && !checkCharacters(registration.value)){
        valid = false;
        registration.classList.add("error-input");
        errorRegistration.innerText = "Pole zawiera niedozwolone znaki";
    }

    // CHECK TYPE
    if (type.value.length > 0 && !checkTextLengthRange(type.value,2,30)){
        valid = false;
        type.classList.add("error-input");
        errorType.innerText = "Pole powinno zawierać od 2 do 30 znaków, lub pozostać puste";
    } else if (type.value.length > 0 && !checkCharacters(type.value)){
        valid = false;
        type.classList.add("error-input");
        errorType.innerText = "Pole zawiera niedozwolone znaki";
    }


    // CHECK COLOR
    if (color.value.length > 0 && !checkTextLengthRange(color.value,2,30)){
        valid = false;
        color.classList.add("error-input");
        errorColor.innerText = "Pole powinno zawierać od 2 do 30 znaków, lub pozostać puste";
    } else if (color.value.length > 0 && !checkCharacters(color.value)){
        valid = false;
        color.classList.add("error-input");
        errorColor.innerText = "Pole zawiera niedozwolone znaki";
    }


    // CHECK OVERALL VALIDITY
    if (!valid) {
        errorsSummary.innerText = "Formularz zawiera błędy";
    }

    return valid;
}