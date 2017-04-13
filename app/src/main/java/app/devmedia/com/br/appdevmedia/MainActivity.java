package app.devmedia.com.br.appdevmedia;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import app.devmedia.com.br.appdevmedia.acync.AcyncUser;
import app.devmedia.com.br.appdevmedia.adapter.ViewPagerAdapter;
import app.devmedia.com.br.appdevmedia.fragments.FragmentPerfil;
import app.devmedia.com.br.appdevmedia.fragments.FragmentProdutos;
import app.devmedia.com.br.appdevmedia.util.Constantes;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Drawer drawer;
    private static final long ID_ND_FOOTER = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        configurarViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        //if you want to update the items at a later time it is recommended to keep it in a variable
        final PrimaryDrawerItem itemPerfil = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName("Perfil")
                .withIcon(GoogleMaterial.Icon.gmd_person)
                .withBadge("Novo")
                .withBadgeStyle(new BadgeStyle()
                        .withTextColor(Color.WHITE)
                        .withColorRes(R.color.md_red_700));
        final PrimaryDrawerItem itemCompras = new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName("Ultimas Compras")
                .withBadge("Novo")
                .withBadgeStyle(new BadgeStyle()
                        .withTextColor(Color.WHITE)
                        .withColorRes(R.color.md_red_700));

        final SecondaryDrawerItem itemProdutos = new SecondaryDrawerItem()
                .withIdentifier(3)
                .withName("Produtos");


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.headle)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("Diogo Gaita")
                                .withEmail("gaita@gmail.com")
                                .withIcon(getResources()
                                        .getDrawable(R.drawable.dioguera))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


//create the drawer and remember the `Drawer` result object
        drawer = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new SectionDrawerItem().withName("Contas de Usuario"),
                        itemPerfil,
                        new SectionDrawerItem().withName("Ações do Sistema"),
                        itemProdutos,
                        itemCompras
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        configureItem(position, drawerItem);
                        return true;
                    }
                })
                .build();
        drawer.addStickyFooterItem(new PrimaryDrawerItem()
                .withName("Sobre o App")
                .withIdentifier(ID_ND_FOOTER));

    }

    private void configureItem(int position, IDrawerItem drawerItem) {
        viewPager.setCurrentItem((int) drawerItem.getIdentifier());
        switch (position) {
            case (int) ID_ND_FOOTER:
                try {
                    PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
                    String versao = info.versionName;
                    Toast.makeText(this, "Versao: " + versao, Toast.LENGTH_SHORT).show();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                break;
        }
        drawer.closeDrawer();
    }

    private void configurarViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FragmentProdutos(), "Produtos");
        viewPagerAdapter.addFragment(new FragmentPerfil(), "Perfil");

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
