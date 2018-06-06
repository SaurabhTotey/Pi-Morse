/**
 * Handles text to morse conversion preview
 */
var previewInput = document.getElementById('text-preview-input');
var previewOutput = document.getElementById('text-preview-output');
previewInput.oninput = function () {
    fetch('/pi-morse?message=' + encodeURI(previewInput.value)).then(function (response) {
        response.text().then(function (body) {
            previewOutput.value = body;
        });
    });
};

/**
 * Handles submitting text to Raspberry Pi LED
 */
var submissionInput = document.getElementById('text-submission-input');
var submissionOutput = document.getElementById('text-submission-output');
var submissionButton = document.getElementById('text-submission-button');
submissionButton.onclick = function () {
    fetch('/pi-morse?message=' + encodeURI(submissionInput.value), { method: 'POST' }).then(function (response) {
        response.json().then(function (result) {
            submissionOutput.value = result.details;
        });
    });
};
submissionInput.onkeypress = function (event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        submissionButton.onclick();
    }
};