function storeData() {
    var dataToPass = window.location.href;
    dataToPass.split("/");
    dataToPass = dataToPass[dataToPass.length];
    console.log(dataToPass);
    localStorage.setItem("dataToPass", dataToPass);
}