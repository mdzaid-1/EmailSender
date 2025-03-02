function sendEmail(type) {
    const formData = new FormData();
    formData.append("to", document.getElementById("to").value);
    formData.append("subject", document.getElementById("subject").value);
    formData.append("text", document.getElementById("message").value);

    if (type === "attachment") {
        const fileInput = document.getElementById("file");
        if (fileInput.files.length > 0) {
            formData.append("file", fileInput.files[0]);
        } else {
            console.log("No file selected!");
        }
    }

    let apiUrl = "http://localhost:8083/email/";
    switch (type) {
        case "single":
            apiUrl += "send";
            break;
        case "multiple":
            apiUrl += "send-multiple";
            break;
        case "attachment":
            apiUrl += "send-with-attachment";
            break;
        default:
            console.error("Invalid email type selected!");
            return;
    }

    console.log("Sending request to:", apiUrl);
    console.log("FormData contents:", [...formData.entries()]);

    fetch(apiUrl, {
        method: "POST",
        body: formData
    })
    .then(response => response.text())
    .then(data => {
        console.log("Response received:", data);
        document.getElementById("responseMessage").innerText = data;
    })
    .catch(error => {
        console.error("Error:", error);
        document.getElementById("responseMessage").innerText = "Error sending email!";
    });
}

