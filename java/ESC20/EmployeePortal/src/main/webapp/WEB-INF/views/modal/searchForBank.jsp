<div
            class="modal fade"
            id="selectBankModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="selectBankModal"
            aria-hidden="true"
            data-backdrop="static"
        >
            <div class="modal-dialog approveForm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button
                        type="button" role="button"
                        class="close"
                        data-dismiss="modal"
                        aria-hidden="true"
                        aria-label="${sessionScope.languageJSON.label.closeModal}"
                        >
                            &times;
                        </button>
                        <h4 class="modal-title">${sessionScope.languageJSON.profile.searchForBanks}</h4>
                    </div>
                    <div class="modal-body">
                        <form
                            action="/"
                            id="selectBank"
                            method="post"
                            class="flex"
                        >
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group">
                                <label class="form-title" for="codeCriteriaSearchCode"
                                    ><span>${sessionScope.languageJSON.profile.routingNumber}</span>:</label
                                >
                                <div class="button-group">
                                    <input
                                        id="codeCriteriaSearchCode"
                                        name="codeCriteriaSearchCode"
                                        class="form-control"
                                        type="text"
                                        value=""
                                    />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" for="codeCriteriaSearchDescription"
                                    ><span>${sessionScope.languageJSON.profile.bankName}</span>:</label
                                >
                                <div class="button-group">
                                    <input
                                        id="codeCriteriaSearchDescription"
                                        name="codeCriteriaSearchDescription"
                                        class="form-control"
                                        type="text"
                                        value=""
                                    />
                                </div>
                            </div>
                            <div class="form-group btn-group">
                                <div style="margin-top:20px;">
                                    <button
                                        type="button" role="button" id="searchBankBtn"
                                        class="btn btn-primary"
                                       
                                    >${sessionScope.languageJSON.label.search}</button>
                                </div>
                            </div>
                        </form>
                        <div class="bankResult">
                            <table class="table border-table" id="bankTable">
                                <thead>
                                    <tr>
                                        <th>${sessionScope.languageJSON.profile.routingNumber}</th>
                                        <th>${sessionScope.languageJSON.profile.bankName}</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                </tbody>
                            </table>
                            <div class="bankPageGroup clearfix">
                                    <div class="pull-right">
                                            <b>${sessionScope.languageJSON.label.rows}:</b>
                                            <span id="totalBank"></span>
                                        </div>
                                <div class="pageGroup">
									<button class="pageBtn firstPate" onclick="changePage(1)" aria-label="${sessionScope.languageJSON.label.firstPage}" disabled>
											<i class="fa fa-angle-double-left "></i>
									</button>  
									<button class="pageBtn prevPage" onclick="changePage(2)" aria-label="${sessionScope.languageJSON.label.prevPage}">
											<i class="fa fa-angle-left "></i>
									</button>
									<select class="selectPage" name="page" id="pageNow" aria-label="${sessionScope.languageJSON.label.choosePage}" onchange="changePage(0)">
									</select>
									<div class="page-list">
											<span class="slash">/</span>
											<span class="totalPate">1</span>
									</div>
									<button class="pageBtn nextPate" onclick="changePage(3)" aria-label="${sessionScope.languageJSON.label.nextPage}">
													<i class="fa fa-angle-right "></i>
									</button>
									<button class="pageBtn lastPate" onclick="changePage(4)" aria-label="${sessionScope.languageJSON.label.lastPage}">
													<i class="fa fa-angle-double-right"></i>
									</button>
                                </div>
                                
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button
                            type="button" role="button"
                            class="btn btn-secondary"
                            data-dismiss="modal"
                            aria-hidden="true"
                           
                        >
                        ${sessionScope.languageJSON.label.cancel}
                        </button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal -->
        </div>