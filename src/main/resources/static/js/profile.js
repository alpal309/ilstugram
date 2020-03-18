(function(){
    console.log(window.sessionStorage);
    createHeader();
    $(".userimg").addEventListener("mouseenter", function(e){
        $("#tnoverlay").style.opacity = "1";
        e.currentTarget.addEventListener("mouseleave", function(){
            $("#tnoverlay").style.opacity = "0";
        })
    })
})();

