const specialCharacters = new RegExp(/^.*[@#?`!';<>{}$].*$/s);


function resetErrors(inputs, errorTexts, errorInfo) {
    for(let i=0; i<inputs.length; i++) {
        inputs[i].classList.remove("error-input");
    }
    for(let i=0; i<errorTexts.length; i++) {
        errorTexts[i].innerText = "";
    }
    errorInfo.innerText = "";
}

function timeStampFromFuture(value) {
    if (!value) {
        return false;
    }
    value = value.toString().trim();

    d_now = new Date();
    d_inp = new Date(value)
    return d_now.getTime() <= d_inp.getTime();
}

function timeStampFromFarPast(value){
    if (!value) {
        return false;
    }
    value = value.toString().trim();

    d_past = new Date("1970-01-01");
    d_past.setHours(0);
    d_past.setMinutes(0);
    d_past.setSeconds(1);

    d_inp = new Date(value)
    return d_inp.getTime() < d_past.getTime();
}

function checkRequired(value) {
    if (!value) {
        return false;
    }
    value = value.toString().trim();
    return !(value === "" || value === "type");


}

function checkCharacters(value){
    if (!value)
        return false;
    value = value.toString().trim();
    return !value.match(specialCharacters);
}

function checkTextLengthRange(value, min, max) {
    if (!value) {
        return false;
    }
    value = value.toString().trim();
    const length = value.length;
    if (max && length > max) {
        return false;
    }
    return !(min && length < min);
}


function checkDateIfAfter(value, compareTo) {
    if (!value) {
        return false;
    }
    if (!compareTo) {
        return false;
    }
    const pattern = /(\d{4})-(\d{2})-(\d{2})/;
    if (!pattern.test(value)) {
        return false;
    }
    if (!pattern.test(compareTo)) {
        return false;
    }
    const valueDate = new Date(value);
    const compareToDate = new Date(compareTo);
    if (valueDate.getTime() < compareToDate.getTime()) {
        return false;
    }
    return true;
}


function checkTimestamp(value) {
    if (!value) {
        return false;
    }
    const pattern = /(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})/;
    return pattern.test(value);
}




