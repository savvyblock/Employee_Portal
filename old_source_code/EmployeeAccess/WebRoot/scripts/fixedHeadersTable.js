/**
 * 
 */

(function($) {
   $.fn.noScrollTableHeader = function() {
      return this.each(function() {
         var $this = $(this),
            $t_fixed;
         function init() {
            $this.wrap('<div id="containerdsfrDiv" class="containerdsfr" />');
            $t_fixed = $this.clone();
            $t_fixed.attr("id","cloned"+$this.attr('id'));
            $t_fixed.find("tbody").end().addClass("fixeddsfr").insertBefore($this).remove();
            resizeFixed();
           $t_fixed.show();
         }
         function resizeFixed() {
           
            $t_fixed.find("th").each(function(index) {
               $(this).css("width",$this.find("th").eq(index).width()+"px");
               $(this).css("min-width",$this.find("th").eq(index).width()+"px");
            });
       
            scrollResultsTableDiv();

            // the code blow is meant to prevent the resultsTableDiv div horizontal scrollbar from appearing.  The conditional
            // tests if the horizontal scrollbar is present. if so, the div's min-width is adjusted and a resize event 
            // triggered to ensure the div has resized wide enough so the div's horizontal scrollbar is not visible
            // the padding had to be reset to 0 since on Chrome, in some instances the padding was being reset
            $('#resultsTableDiv').css('padding-left',0);
        	$('#resultsTableDiv').css('padding-right',0);
            if (($("#resultsTableDiv").get(0).clientWidth) < $("#resultsTableDiv").get(0).scrollWidth) {
            	$('#resultsTableDiv').css('min-width',$("#resultsTableDiv").get(0).clientWidth+30);
            	
            	$('body').css('min-width',$("#resultsTableDiv").get(0).clientWidth+50);
            	$('html').css('min-width',$("#resultsTableDiv").get(0).clientWidth+50);
            	
            	$(window).trigger('resize');  // keep resizing until the widths are adjusted correctly
            }
         }
        
         function scrollFixed() {
         }

         function scrollResultsTableDiv() {
        	 if(navigator.userAgent.toLowerCase().indexOf('firefox') != -1) {
        		 // for the Firefox browser, when scrolling the resultsTableDiv adjust the value of the 
        		 // top position for the clonedresultsTable top position; this is not necessary for IE or Chrome
            	 var resultsTablePos = $("#resultsTable").position();
            	 var resultsTableDivScrollTop = $("#resultsTableDiv").scrollTop();
                 $("#clonedresultsTable").css({left:resultsTablePos.left, top:resultsTablePos.top + resultsTableDivScrollTop});
// another way to do the same thing
//        		 var clonedResultsTableOffset = $("#clonedresultsTable").offset();
//        		 clonedResultsTableOffset.top = resultsTablePos.top + resultsTableDivScrollTop;
//        		 $( "#clonedresultsTable" ).offset(clonedResultsTableOffset);
        	 }
          }

        $(window).resize(resizeFixed);
		$(window).scroll(scrollFixed);
		$("#resultsTableDiv").scroll(scrollResultsTableDiv);

	    init();
      });
   };
})(jQuery);

$(document).ready(function(){

	$("#resultsTable").noScrollTableHeader();	
	
	// this resize is related to the processing in the noScrollTableHeader() resizeFixed callback
	// to prvent the div horizontal scrollbar from appearing in favor of the browser horizontal scrollbar;
	$(window).trigger('resize');
});

