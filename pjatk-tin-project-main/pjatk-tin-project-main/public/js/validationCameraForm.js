function validateForm() {

    //elements
    const alias = document.getElementById('alias');
    const location = document.getElementById("location");
    const manufacturer = document.getElementById("manufacturer");
    const resolution = document.getElementById("resolution");

    //errors
    const errorAlias = document.getElementById('errorAlias');
    const errorLocation = document.getElementById('errorLocation');
    const errorManufacturer = document.getElementById('errorManufacturer');
    const errorResolution = document.getElementById('errorResolution');

    const errorsSummary = document.getElementById('errorsSummary');

    // reset errors
    resetErrors([alias, location, manufacturer, resolution], [errorAlias, errorLocation, errorManufacturer, errorResolution], errorsSummary);

    let valid = true;

    if (!checkRequired(alias.value)) {
        valid = false;
        alias.classList.add("error-input");
        errorAlias.innerText = "Pole jest wymagane";
    } else if (!checkTextLengthRange(alias.value, 2, 30)) {
        valid = false;
        alias.classList.add("error-input");
        errorAlias.innerText = "Pole powinno zawierać od 2 do 30 znaków";
    } else if (alias.value.length > 0 && !checkCharacters(alias.value)){
        valid = false;
        alias.classList.add("error-input");
        errorAlias.innerText = "Pole zawiera niedozwolone znaki";
    }

    // location check
    if(location.value.length > 0 && !checkTextLengthRange(location.value,2,30)){
        valid = false;
        location.classList.add("error-input");
        errorLocation.innerText = "Pole powinno zawierać od 2 do 30 znaków lub pozostać puste";
    } else if (location.value.length > 0 && !checkCharacters(location.value)){
        valid = false;
        location.classList.add("error-input");
        errorLocation.innerText = "Pole zawiera niedozwolone znaki";
    }

    // manufacturer check
    if(manufacturer.value.length > 0 && !checkTextLengthRange(manufacturer.value,2,30)){
        valid = false;
        manufacturer.classList.add("error-input");
        errorManufacturer.innerText = "Pole powinno zawierać od 2 do 30 znaków lub pozostać puste";
    } else if (manufacturer.value.length > 0 && !checkCharacters(manufacturer.value)){
        valid = false;
        manufacturer.classList.add("error-input");
        errorManufacturer.innerText = "Pole zawiera niedozwolone znaki";
    }

    // resolution check
    if(resolution.value.length > 0 && !checkTextLengthRange(resolution.value,2,30)){
        valid = false;
        resolution.classList.add("error-input");
        errorResolution.innerText = "Pole powinno zawierać od 2 do 30 znaków lub pozostać puste";
    } else if (resolution.value.length > 0 && !checkCharacters(resolution.value)){
        valid = false;
        resolution.classList.add("error-input");
        errorResolution.innerText = "Pole zawiera niedozwolone znaki";
    }

    if (!valid) {
        errorsSummary.innerText = "Formularz zawiera błędy";
    }

    return valid;
}


