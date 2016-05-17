package in.co.dineout.xoppin.dineoutcollection.model.pages;

import in.co.dineout.xoppin.dineoutcollection.model.AbstractWizardModel;

/**
 * Callback interface connecting {@link Page}, {@link AbstractWizardModel}, and model container
 * objects.
 */
public interface ModelCallbacks {
    void onPageDataChanged(Page page);
    void onPageTreeChanged();
}
