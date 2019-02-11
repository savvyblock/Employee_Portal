$(document).ready(function() {

	// Arrow icon Page Change
	$('.unsavedDataCheck').click(function() {
		unsavedDataCheck(this, 'click');
	});

	// Dropdown Page Change
	$('.unsavedDataCheckOnChange').change(function(e) {
		unsavedDataCheck(this, 'change');
	});
});

var UnsavedDataWarning = function() {
	var dataHasChanged = loadState();
	var warningEnabled = true;
	var tab_action = false;

	window.onbeforeunload = function() {
		evaluateState();
		if (dataHasChanged && warningEnabled) {
			if (typeof(MetexAction) != "undefined") {
				setTimeout(function() { MetexAction.unsetAction(); }, 0);
			}
			return "You will lose any unsaved data.";
		}

	};
	
	function createTrackedInput() {
		var node = this;
		var tag = node.tagName;
		
		function getInputValue() {
			if (tag == "INPUT") {
				if (node.getAttribute("type") == "checkbox") {
					return node.checked;
				}
			}
			return $(node).val();
		}
		
		var initialValue = "";
		
		this.load = function() {
			initialValue = getInputValue();
		}

		if (tag == "INPUT") {		
			$(node).change(function() {
				if ($(this).parents(".ignore_changes").length > 0) {
					return;
				}
				if (getInputValue() != initialValue) {
					dataHasChanged = true;
				}
			});
		}
		else if (tag == "SELECT") {
			$(node).change(function() {
				if ($(this).parents(".ignore_changes").length > 0) {
					return;
				}
				if (getInputValue() != initialValue) {
					dataHasChanged = true;
				}
			});					
		}
	}
	
	function evaluateState() {
		if (dataHasChanged == false) {
			if ($("[unsavedDataChanges='true']").length > 0 ) {
				dataHasChanged = true;
			} 
			//else if ($(".error").length > 0) {
			//	dataHasChanged = true;
			//}
			
			if (typeof tinyMCE != 'undefined' && typeof tinyMCE.activeEditor.inst  != 'undefined' && tinyMCE.activeEditor.inst == true )
			{
				dataHasChanged = true;
			}
			
		}
		
		
	}
	
	function loadState() {
		var state = false;
		var isTabAction = false;

		var cookies = document.cookie.split(";");
		for (var i = 0; i < cookies.length; i++) {
			if (cookies[i].split("=")[0].trim() == "unsavedChanges") {
				state = (cookies[i].split("=")[1].trim() == "true");
			}
			if (cookies[i].split("=")[0].trim() == "tabAction") {
				isTabAction = (cookies[i].split("=")[1].trim() == "true");
			}
		}
		if(!isTabAction)
			document.cookie = "unsavedChanges=false;path=/" + getApplicationName();
		else
			document.cookie = "tabAction=false;path=/" + getApplicationName();
		
		return state;
	}
	
	function getApplicationName() {
		return window.location.toString().split("/")[3];
	}

	return {
		
		initialize : function(scopeElement) {
			var trackedInputs = $("input, select, textarea").not(".row_selector").not(".ignore_changes");
			trackedInputs.each(createTrackedInput);
			$(window).load(function() {
				trackedInputs.each(function() {
					this.load();
				});
			});
			
			$(".navigation_button").unbind("click");
			$(".navigation_button").click(function() {			
					if (dataHasChanged && warningEnabled) {
						if($(this).hasClass("force_clean"))
						{
							dataHasChanged = false;
						}
					
						return confirm("Are you sure you want to navigate away from this page? \n\n" 
										+ "You will lose any unsaved data. \n\n" +
										" Press OK to continue, or Cancel to stay on the current page.");
					}
				});
							
			$("form", scopeElement).submit(function() {
					UnsavedDataWarning.disable();
				});
			
			if ($(".force_change_warning").length > 0) {
				dataHasChanged = true;
			}
		},
	
		enable : function() {
			warningEnabled = true;
		},
		
		disable : function() {
			warningEnabled = false;
		},
		
		forceDirty : function() {
			dataHasChanged = true;
			UnsavedDataWarning.saveState();
		},
		
		forceDirtyTransient : function() {
			dataHasChanged = true;
		},
		
		forceCleanTransient : function()
		{
			dataHasChanged = false;
		},
		
		forceClean : function() 
		{
			UnsavedDataWarning.forceCleanTransient();
			UnsavedDataWarning.saveState();
		},
		
		saveState : function() {
			document.cookie = "unsavedChanges=" + dataHasChanged + ";path=/" + getApplicationName();
		},
		
		saveTabState : function() {
			document.cookie = "unsavedChanges=" + dataHasChanged + ";path=/" + getApplicationName();
			document.cookie = "tabAction=true" + ";path=/" + getApplicationName();
		},
		
		isDirty : function() {
			return dataHasChanged; 
		}	
	}
}();

Behaviors.register(UnsavedDataWarning);

//***********************************************************************
function unsavedDataCheck(control, event) {

	var controlButton = "";
	var controlName = control.id;

	// Pagination Arrows
	if (controlName == "FirstPage" || controlName == "PreviousPage" || controlName == "NextPage" || controlName == "LastPage") {

		var currentPage = $("#CurrentPage").val();
		currentPage = parseInt(currentPage);

		var totalPages = $("#TotalPages").val();
		totalPages = parseInt(totalPages);

		if (controlName == "FirstPage") {
			currentPage = 1;
		}
		else if (controlName == "PreviousPage") {
			currentPage = (currentPage > 1) ? (currentPage - 1) : currentPage; 
		}
		else if (controlName == "NextPage") {
			currentPage = (currentPage < totalPages) ? (currentPage + 1) : currentPage;
		}
		else if (controlName == "LastPage") {
			currentPage = totalPages;
		}

		// Single button to handle pagination arrows
		controlButton = "PaginationButton";

		// Set the new page to paginate.
		$('#SelectedPage').val(currentPage);
	}

	// Retrieve, Print and Sort
	else {
		controlButton = control.id + "Button";
	}

	if ( UnsavedDataWarning.isDirty() || $('#tabpageHasDataChange').html() == 'true' ) {
		var msg = 	"You have changes that have not been saved.</br></br>" +
					"If you choose to continue, you will lose any unsaved data.</br></br>" +
					"Press OK to continue, or Cancel to stay on the current page.";

		var result = $("#dialog-popup").html(msg).dialog({
						modal: true,
						resizable: false,
						width: 450,
						height: 'auto',
						closeOnEscape: false,
						title: "Warning",
						position: { my: "center", at: "center", of: window },
						open: function(event, ui) {

							// Remove X on Tile Bar
							$(".ui-dialog-titlebar-close").hide();

							// Default to Cancel Button
							$(this).siblings('.ui-dialog-buttonpane').find('button:eq(1)').focus();
						},
						buttons: {
							OK : function() {
								$(this).dialog("close");
								$("#dialog:ui-dialog").dialog("destroy");
								UnsavedDataWarning.forceClean();
								$('#' + controlButton).click();
							},

							Cancel : function() {
								$(this).dialog("close");
								$("#dialog:ui-dialog").dialog("destroy");

								// Revert back to the previous page when changed.
								$('#SelectedPage').val($('#CurrentPage').val());
								UnsavedDataWarning.enable();
								//Revert input values to original values
								var arrLength = originalID.length;
								for (var i = 0; i < arrLength; i++) {
									$("#" + originalID[i]).val(originalValue[i]);
								}
							}
						}
					});
		return;
	}

	$('#' + controlButton).click();
};