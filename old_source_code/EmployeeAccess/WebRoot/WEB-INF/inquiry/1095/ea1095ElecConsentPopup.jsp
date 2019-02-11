<div id="dialog-message-EA1095ConsentPopup" title="1095 Electronic Consent" style="display: none;">
	<table>
		<tr>
			<td id="errorTag" class="hidden">
				<font color="red">Please select a valid option.</font>
			</td>
		</tr>
		<tr>
			<td>
				<textarea id="ea1095ElecConsntPopup" cols="100" rows="15" style="resize:none" disabled="true"></textarea>
			</td>
		</tr>
	</table>
	</br>
	<span style="padding-left: 10px;">
		<input 	type="radio" 
				id="yesToElecAccess"
				name="yesToElecAccess"
				value="false"
				tabindex="1" 
				onclick="UnsavedDataWarning.disable();toggleOptions(this.id, this.value);updateEA1095ElecConsent(this.id)"/>&nbsp;Yes, I consent to electronic access. I agree to access my 1095 Form electronically as described above and </br>
					print my 1095 Form. I do not need a printed copy furnished to me.
	</span>

	</br>
	</br>

	<span style="padding-left: 10px;">
		<input 	type="radio" 
				id="noToElecAccess"
				name="noToElecAccess"
				value="false"
				tabindex="1"
				onclick="UnsavedDataWarning.disable();toggleOptions(this.id, this.value);updateEA1095ElecConsent(this.id)"/>&nbsp;No, I do not consent to electronic access. I am requesting a paper copy furnished to me.
	</span>
</div>