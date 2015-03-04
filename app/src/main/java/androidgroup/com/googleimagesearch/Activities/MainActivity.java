package androidgroup.com.googleimagesearch.Activities;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidgroup.com.googleimagesearch.Adapters.ImageAdapter;
import androidgroup.com.googleimagesearch.Interface.DialogFragmentListener;
import androidgroup.com.googleimagesearch.Models.ImageSearch;
import androidgroup.com.googleimagesearch.R;


public class MainActivity extends ActionBarActivity  implements DialogFragmentListener {

    public static GridView gdvImage;
    public static List<ImageSearch> images = new ArrayList<ImageSearch>();
    public static ImageAdapter adapter;
    public static HashMap<String, String> dialogValues = new HashMap<String, String>();
    public static String imageSize = null;
    public static String imageColor = null;
    public static String imageType = null;
    public static String enteredQuery=null;
    public static LoadMore loadMoreListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                enteredQuery = query;
                adapter.clear();
                getImagesForQuery(query,1);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    public static String buildUrl(String query,int page) {
        StringBuilder builder = new StringBuilder();
        String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query+"&start="+((page-1) * 4);
        builder.append(url);
        imageSize = dialogValues.get("ImageSize");
        imageColor = dialogValues.get("ImageColor");
        imageType = dialogValues.get("ImageType");
        if (imageSize != "any") {
            builder.append("&imgsz=" + imageSize);
        }
        if (imageColor != "any") {
            builder.append("&imgcolor=" + imageColor);
        }
        if (imageType != "any") {
            builder.append("&imgtype=" + imageType);
        }
        return builder.toString();
    }

    @Override
    public void onReturnValue(HashMap<String, String> map) {
        dialogValues = map;
        adapter.clear();
        getImagesForQuery(enteredQuery,1);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SettingsDialog settingsDialog = new SettingsDialog();
            settingsDialog.show(getFragmentManager(), "fragment_edit_name");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void getImagesForQuery(String query,int page) {
        if("".equals(query)) return;
        // String query = txtQuery.getText().toString();
        if(page ==1){
            loadMoreListener.resetCounter();
        }
        String url = buildUrl(query,page);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("response", response.toString());
                try {
                    JSONObject json = response.getJSONObject("responseData");
                    List<ImageSearch> imageSearchResult = ImageSearch.getImages(json.getJSONArray("results"));
                    images.addAll(imageSearchResult);
                    adapter.notifyDataSetChanged();
                    Log.i("images", images.toString());
                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            gdvImage = (GridView) rootView.findViewById(R.id.gdvImage);

            loadMoreListener = new LoadMore() {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    Log.i("DEBUG", "LOADMORE " + page + " " + totalItemsCount);
                    getImagesForQuery(enteredQuery,page);
                }
            };
            gdvImage.setOnScrollListener(loadMoreListener);

            gdvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ImageSearch image = images.get(position);
                    Intent i = new Intent(getActivity(), ImageActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("url", image.url);
                    startActivity(i);
                }
            });

            adapter = new ImageAdapter(getActivity(), images);
            gdvImage.setAdapter(adapter);

            return rootView;
        }


    }

}