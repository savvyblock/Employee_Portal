var myDate = new Date()
var year = myDate.getFullYear()
var month = myDate.getMonth() + 1
var date = myDate.getDate()
var h = myDate.getHours() //get h (0-23)
var m = myDate.getMinutes() //get m (0-59)
var s = myDate.getSeconds()
if (parseInt(h) < 12) {
    var now =
        getNow(month) +
        '-' +
        getNow(date) +
        '-' +
        year +
        ' ' +
        getNow(h) +
        ':' +
        getNow(m) +
        ' ' +
        'AM'
} else {
    var hour = parseInt(h) - 12
    var now =
        getNow(month) +
        '-' +
        getNow(date) +
        '-' +
        year +
        ' ' +
        getNow(hour) +
        ':' +
        getNow(m) +
        ' ' +
        'PM'
}

function getNow(s) {
    return s < 10 ? '0' + s : s
}
document.getElementById('date-now').innerHTML = now
function submitEarning(){
    $("#selectEarnings")[0].submit();
}
$(function() {
    let showEarningDetail = false
    let showDeductionsDetail = false
    $(".totalEarningBtn").click(function(){
        if(!showEarningDetail){
            $(".earning-detail").show()
            $(this).html("Close <")
            showEarningDetail = true
        }else{
            $(".earning-detail").hide()
            $(this).html("Detail >")
            showEarningDetail = false
        }
        
    })
    $(".totalDeductionsBtn").click(function(){
        if(!showDeductionsDetail){
            $(".deduction-table").show()
            $(this).html("Close <")
            showDeductionsDetail = true
        }else{
            $(".deduction-table").hide()
            $(this).html("Detail >")
            showDeductionsDetail = false
        }
    })
})

function downloadPDF() {
    $('.exportPDFBox').show()
    $('.exportPDFBox').addClass("printStatus")
    $('.exportPDFBox').append($('.needToClone').clone())
    var shareContent = $('.exportPDFBox')[0]

    let pdfDom = '.exportPDFBox .needToClone'
    let fileName = 'earning'
    let title = $(document).attr("title")
    convert2canvasDownload(shareContent,pdfDom,fileName,title)
}
// function downAsPdf(){


//     const SIZE = [595.28,841.89];  //a4宽高
//     let node = document.getElementById("exportPDFBox");
//     let nodeH = node.clientHeight;
//     let nodeW = node.clientWidth;
//     let pageH = nodeW / SIZE[0] * SIZE[1];
//     let modules = node.childNodes;
//     console.log(modules)
//     let pageFooterH = 50;  //50为页尾的高度
//     // this.addCover(node.childNodes[0],pageH);  //添加封面
//     this.addPageFooter(node.childNodes[1],1,0);  //添加页尾
//     this.addPageHeader(node.childNodes[2]);  //添加页头
//     for(let i = 0,len = modules.length;i < len;i++){
//         let item = modules[i];
//         console.log(item.innerHTML)
//         if(!item.innerHTML || item.innerHTML==''){
//             console.log(item.innerHTML)
//         }else{

       
    
//         //div距离body的高度是pageH的倍数x,但是加上自身高度之后是pageH的倍数x+1
//             let beforeH = item.offsetTop + pageFooterH;
//             let afterH = beforeH + item.clientHeight + pageFooterH;  
//             console.log(beforeH)
//             console.log(pageH)
//             console.log(parseInt(beforeH/pageH))
//             console.log(parseInt(afterH/pageH))
//             let currentPage = parseInt(beforeH/pageH);
//             if(currentPage != parseInt(afterH/pageH)){
//                 //上一个元素底部距离body的高度
//                 console.log(modules[i-1])
//                 let lastItemAftarH = modules[i-1].offsetTop + modules[i-1].clientHeight; 
//                 let diff = pageH - lastItemAftarH%pageH - pageFooterH;  
//                 //加页尾
//                 this.addPageFooter(item,currentPage+1,diff);
//                 //加页头
//                 this.addPageHeader(item);
//             }
//             if(i == len-1){
//                 let diff = pageH - afterH%pageH + 20;  //50为页尾的高度
//                 //加页尾
//                 this.addPageFooter(item,currentPage+1,diff,true);
//             }
//         }
//     }

//     const WGUTTER = 20;  //横向页边距
//     html2canvas(node).then(function(canvas) {
//         var contentWidth = canvas.width;
//         var contentHeight = canvas.height;
//         //一页pdf显示html页面生成的canvas高度;
//         var pageHeight = contentWidth / SIZE[0] * SIZE[1];
//         //未生成pdf的html页面高度
//         var leftHeight = contentHeight;
//         //html页面生成的canvas在pdf中图片的宽高
//         var imgWidth = SIZE[0]- WGUTTER*2;
//         var imgHeight = imgWidth / contentWidth * contentHeight;
//         var pageData = canvas.toDataURL('image/jpeg', 1.0);
//         var pdf = new jsPDF('', 'pt', 'a4', true);
//         //pdf页面竖向偏移
//         var position = 0;
//         if (leftHeight < pageHeight) {
//             pdf.addImage(pageData, 'JPEG', WGUTTER, position, imgWidth, imgHeight, 'FAST' );
//         } else {
//             while(leftHeight > 0) {
//                 pdf.addImage(pageData, 'JPEG', WGUTTER, position, imgWidth, imgHeight)
//                 leftHeight -= pageHeight;
//                 position -= SIZE[1];
//                 //避免添加空白页
//                 if(leftHeight > 0) {
//                     pdf.addPage();
//                 }
//             }
//         }
//         pdf.save('content.pdf');
//     })
// }

// function addCover(node,pageH){
//     let cover = document.createElement("div");
//     cover.className = "c-page-cover";
//     cover.style.height = (pageH-50)+"px";
//     cover.innerHTML = `<img src="./img/logo.png" />
//                       <table>
//                         <tbody>
//                           <tr><td>pdf name</td></tr>   
//                           <tr><td>pdf报告生成时间</td></tr>                                
//                         </tbody>
//                       </table>`;
//     node.parentNode.insertBefore(cover,node);
//   }

//   function addPageHeader(item) {
//     let pageHeader = document.createElement("div");
//     pageHeader.className = "c-page-head";
//     pageHeader.innerHTML = "页头内容";
//     item.parentNode.insertBefore(pageHeader,item);
//   }

//   function addPageFooter(item,currentPage,diff,isLastest){
//     let pageFooter = document.createElement("div");
//     pageFooter.className = "c-page-foot";
//     pageFooter.innerHTML = "第 "+ currentPage +" 页 ";
//     isLastest?item.parentNode.insertBefore(pageFooter,null):item.parentNode.insertBefore(pageFooter,item);
//     pageFooter.style.marginTop = diff+"px";
//     pageFooter.style.marginBottom = "10px";
//   }