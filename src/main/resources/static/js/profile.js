(function(){
    createHeader();
    //check for uri parameters, if there are any then render the page accordingly,
    //else if not make sure local/server session are equal before rendering profile elements

    fetch("/getThumbnail").then(response => response.blob()).then(blob => {
        if(blob.size !== 0) {
            $(".emptytn").remove();
            $(".userimg").style.backgroundImage = `url('${URL.createObjectURL(blob)}')`;
        }
    }).catch(error => console.log(error));

    $("#thumbnail").addEventListener("change", function(){
        let formdata = new FormData();
        let file = $("#thumbnail").files[0];
        formdata.append("file", file);

        fetch("/uploadThumbnail", {method: "POST", body: formdata}).then(response => response.blob()).then(blob =>{
            $(".userimg").style.backgroundImage = `url('${URL.createObjectURL(blob)}')`;
            $(".emptytn").remove();
        }).catch(error => {
            console.log(error);
        });

    })


})();

