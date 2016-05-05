package in.co.dineout.xoppin.dineoutcollection.model;

import android.content.Context;

import in.co.dineout.xoppin.dineoutcollection.model.pages.BasicDetailPage;
import in.co.dineout.xoppin.dineoutcollection.model.pages.ContactPage;
import in.co.dineout.xoppin.dineoutcollection.model.pages.CuisinePage;
import in.co.dineout.xoppin.dineoutcollection.model.pages.PageList;
import in.co.dineout.xoppin.dineoutcollection.model.pages.RestDetailPage;
import in.co.dineout.xoppin.dineoutcollection.model.pages.TimingPage;

/**
 * Created by prateek.aggarwal on 5/3/16.
 */
public class RestaurantWizardModel extends AbstractWizardModel {


    public RestaurantWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {

        PageList list = new PageList();
        list.add(new BasicDetailPage(this,"Basic Detail").setRequired(true));
        list.add(new RestDetailPage(this, "Details").setRequired(true));
        list.add(new ContactPage(this,"Contact").setRequired(true));
        list.add(new TimingPage(this,"Timing"));

        list.add(new CuisinePage(this, "Cuisine").setRequired(true));
//        list.add(new ImagePage(this,"Image").setRequired(true));
        return list;
    }
}
