function changePage(page){
    if(page){
            $("#selectPageNow").val(page)
    }else{
            var pageNow = $("#pageNow").val();
            $("#selectPageNow").val(pageNow)

    }
    $("#changePageForm")[0].submit()
    
}