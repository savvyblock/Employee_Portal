<!-- add frame for document storage -->
             <div id="divDocStorPopup" class="docStorPopup" style="display: none;">
                <div class="text-right" style="border-bottom:1px solid #e9ecef; padding:5px 0;">
			    	<a id="divDocStorPopupClose" class="docStorPopupClose"><i class="fa fa-close" style="font-size:24px;"></i></a>
			    </div>
	            <div class="faspinner text-center font-24" id="faspinnerIcon">
	                <i class="fa fa-spinner fa-spin" aria-hidden="true"></i>
	            </div>
			    <iframe id="ifr" name="ifr" width="100%" height="100%" src="" style="display: none; border:0;"></iframe>
			</div>
			<div id="popup_mask" class="popupMask" style="display: none; opacity: 0.5;">
			</div>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/modal/travelDocument.js"></script>