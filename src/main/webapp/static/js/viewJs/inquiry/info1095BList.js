function changePage(page){
    if(page){
            $("#selectPageNowB").val(page)
    }else{
            var pageNow = $("#pageNow").val();
            $("#selectPageNowB").val(pageNow)

    }
    $("#changePageFormB")[0].submit()
    
}