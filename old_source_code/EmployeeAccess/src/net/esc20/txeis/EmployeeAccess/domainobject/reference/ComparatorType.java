/**
 * 
 */
package net.esc20.txeis.EmployeeAccess.domainobject.reference;

/**
 * @author bbeverly
 *
 */
public enum ComparatorType {
		
		EQUAL ("Equals", "EQUAL"), 
		GT ("Greater Than", "GT"), 
		LT ("Less Than", "LT"), 
		GTE ("> Than or Equal", "GTE"), 
		LTE ("< Than or Equal", "LTE"), 
		NOTEQUAL ("Doesn't Equal", "NOTEQUAL");
		
		private final String display;
		private final String operator;
		
		/**
		 * @return the display
		 */
		public String getDisplay() {
			return display;
		}

		/**
		 * @return the operator
		 */
		public String getOperator() {
			return operator;
		}

		/**
		 * @param display
		 * @param operator
		 */
		ComparatorType(String display, String operator) {
			this.display = display;
			this.operator = operator;
		}
}
