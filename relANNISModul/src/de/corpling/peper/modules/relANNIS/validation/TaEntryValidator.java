/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.corpling.peper.modules.relANNIS.validation;

import de.corpling.peper.modules.relANNIS.TAObject;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link java.util.Map.Entry}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface TaEntryValidator {
	boolean validate();

	boolean validateTypedKey(Long value);
	boolean validateTypedValue(EList<TAObject> value);
}
