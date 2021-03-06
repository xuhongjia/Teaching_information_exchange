package cn.horry.teaching_information_exchange.listener;

import java.util.List;

import android.os.Handler;
import android.os.Message;

import cn.horry.teaching_information_exchange.adapter.CommonBaseAdapter;
import cn.horry.teaching_information_exchange.widget.PullToRefreshLayout;

public abstract class PullRefreshListener<T> implements PullToRefreshLayout.OnRefreshListener
{
	private List<T> data;
	CommonBaseAdapter adapter;
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==200)
			{
				((PullToRefreshLayout)msg.obj).loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
			else if(msg.what==100)
			{
				((PullToRefreshLayout) msg.obj).refreshFinish(PullToRefreshLayout.SUCCEED);
			}
		}
	};
	public PullRefreshListener(){
		
	}
	public PullRefreshListener( CommonBaseAdapter adapter){
		this.adapter = adapter;
	}
	public PullRefreshListener(List<T> data, CommonBaseAdapter adapter){
		this.data=data;
		this.adapter=adapter;
	}
	public PullRefreshListener(List<T> data, CommonBaseAdapter adapter, Handler handler){
		this.data=data;
		this.adapter=adapter;
		this.handler=handler;
	}
	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout)
	{
		// 下拉刷新操作
		 Message msg = new Message();
		msg.what=100;
		msg.obj = pullToRefreshLayout;
		updataRefresh(adapter, data, msg,handler);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
	{
		Message msg = new Message();
		msg.what=200;
		msg.obj = pullToRefreshLayout;
		updataLoadMore(adapter, data,msg,handler);
	}

	/**
	 * 加载最新
	 * @param adapter
	 */
	public abstract void updataRefresh(CommonBaseAdapter adapter,List<T> data,Message msg ,Handler handler);

	/**
	 * 加载更多
	 * @param adapter
	 */
	public abstract void updataLoadMore(CommonBaseAdapter adapter,List<T> data,Message msg ,Handler handler);


}
