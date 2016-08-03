package dd.com.loadmore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * 列表往下无限加载
 */
public class LoadMoreScrollListener implements OnScrollListener
{
	private int visibleThreshold = 1;
	private boolean mLoading = false;
	private View mFooter;
	private EndlessListener mListener;
	private ListView mLoadingViewContainer;
	private boolean mLastItemVisible;

	/**
	 * 列表到最后时触发监听器
	 * 
	 * @author <a href="mailto:itvincent@gmail.com">Vincent</a>
	 * 
	 *         Build at 2013-12-3
	 */
	public static interface EndlessListener
	{
		/**
		 * 列表到最后时触发监听器加载数据
		 */
		public void onLoadData();
		
		/**
		 * 是否需要加载数据
		 * @return
		 */
		public boolean shouldLoadData();
	}

	/**
	 * 
	 * @param loadingViewContainer
	 */
	public LoadMoreScrollListener(ListView loadingViewContainer, int resourceId)
	{
		this.mLoadingViewContainer = loadingViewContainer;
		setLoadingView(resourceId);
	}

	/**
	 * 
	 * @param listener
	 */
	public void setListener(EndlessListener listener)
	{
		this.mListener = listener;
	}



	protected void setLoadingView(int resourceId)
	{
		if (mLoadingViewContainer != null)
		{
			LayoutInflater inflater = (LayoutInflater) mLoadingViewContainer.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mFooter = inflater.inflate(resourceId, null);
		}
	}

	/**
	 * 最到底前第几行数据就开始加载
	 * 
	 * @param visibleThreshold
	 */
	public void setVisibleThreshold(int visibleThreshold)
	{
		this.visibleThreshold = visibleThreshold;
	}

	/**
	 */
	public void onLoadComplete()
	{
		mLoading = false;
		if (mLoadingViewContainer != null)
		{
			mLoadingViewContainer.removeFooterView(mFooter);
		}
	}

	protected void onLoading()
	{
		mLoading = true;
		if (mLoadingViewContainer != null)
		{
			mLoadingViewContainer.addFooterView(mFooter);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
		mLastItemVisible = (totalItemCount > 0) && 
				(firstVisibleItem + visibleItemCount >= totalItemCount - visibleThreshold);

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& null != mListener 
				&& mLastItemVisible 
				&& !mLoading
				&& mListener.shouldLoadData())
		{
			onLoading();
			mListener.onLoadData();
		}

	}

    public void setLastItemVisible(boolean visible) {
        mLastItemVisible = visible;
    }
}
