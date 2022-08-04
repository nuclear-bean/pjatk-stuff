function validateForm() {
    const registration = document.getElementById('Car_registration');
    const cameraId = document.getElementById('Camera_id');
    const time = document.getElementById('time');
    const authorized = document.getElementById('authorized');
    const direction = document.getElementById('direction');


    const errorRegistration = document.getElementById('errorRegistration');
    const errorCamera = document.getElementById('errorCamera');
    const errorTime = document.getElementById('errorTime');
    const errorAuthorized = document.getElementById('errorAuthorized');
    const errorDirection = document.getElementById('errorDirection');

    const errorsSummary = document.getElementById('errorsSummary');

    let valid = true;

    resetErrors([registration, cameraId, time,authorized,direction], [errorRegistration, errorCamera, errorTime,errorAuthorized,errorDirection], errorsSummary);

    if (!checkRequired(registration.value)) {
        valid = false;
        registration.classList.add("error-input");
        errorRegistration.innerText = "Pole jest wymagane";
    } else if (!checkTextLengthRange(registration.value,2,10)) {
        valid = false;
        registration.classList.add("error-input");
        errorRegistration.innerText = "Pole powinno mieć zawierać od 2 do 10 znaków";
    }

    if (!checkRequired(cameraId.value)) {
        valid = false;
        cameraId.classList.add("error-input");
        errorCamera.innerText = "Pole jest wymagane";
    }
    if (!checkRequired(time.value)) {
        valid = false;
        time.classList.add("error-input");
        errorTime.innerText = "Pole jest wymagane";
    } else if (!checkTimestamp(time.value)) {
        valid = false;
        time.classList.add("error-input");
        errorTime.innerText = "Pole powinno mieć format yyyy-mm-dd hh:mm:ss";
    }
    else if (timeStampFromFuture(time.value)){
        valid = false;
        time.classList.add("error-input");
        errorTime.innerText = "Podana data nie może być z przyszłości";
    }
    else if(timeStampFromFarPast(time.value)){
        valid = false;
        time.classList.add("error-input");
        errorTime.innerText = "Data nie może być starsza niż 1970-01-01 00:00:01";
    }

    if(!checkRequired(authorized.value)){
        valid = false;
        authorized.classList.add("error-input");
        errorAuthorized.innerText = "Pole jest wymagane";
    }

    if(!checkRequired(direction.value)){
        valid = false;
        direction.classList.add("error-input");
        errorDirection.innerText = "Pole jest wymagane";
    }

    if (!valid) {
        errorsSummary.innerText = "Formularz zawiera błędy";
    }

    return valid;
}

resetErrors([registration, cameraId, time], [errorRegistration, errorCamera, errorTime], errorsSummary);

