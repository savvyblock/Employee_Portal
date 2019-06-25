function changePage(page){
    if(page){
            $("#selectPageNowC").val(page)
    }else{
            var pageNow = $("#pageNow").val();
            $("#selectPageNowC").val(pageNow)

    }
    $("#changePageFormC")[0].submit()
    
}