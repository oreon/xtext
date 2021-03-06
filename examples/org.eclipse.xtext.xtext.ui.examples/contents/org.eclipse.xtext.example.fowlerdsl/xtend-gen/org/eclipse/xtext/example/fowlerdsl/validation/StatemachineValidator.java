/**
 * generated by Xtext
 */
package org.eclipse.xtext.example.fowlerdsl.validation;

import org.eclipse.xtext.example.fowlerdsl.statemachine.StatemachinePackage;
import org.eclipse.xtext.example.fowlerdsl.validation.AbstractStatemachineValidator;
import org.eclipse.xtext.validation.Check;

/**
 * Custom validation rules.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@SuppressWarnings("all")
public class StatemachineValidator extends AbstractStatemachineValidator {
  public final static String INVALID_NAME = "invalidName";
  
  @Check
  public void checkGreetingStartsWithCapital(final org.eclipse.xtext.example.fowlerdsl.statemachine.State state) {
    String _name = state.getName();
    char _charAt = _name.charAt(0);
    boolean _isUpperCase = Character.isUpperCase(_charAt);
    if (_isUpperCase) {
      String _name_1 = state.getName();
      this.warning("Name should start with a lower case letter", 
        StatemachinePackage.Literals.STATE__NAME, 
        StatemachineValidator.INVALID_NAME, _name_1);
    }
  }
}
