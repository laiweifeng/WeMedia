package com.wei.news.sdk.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * 
 * @author laiweifeng
 *
 *	所用知识：单例、工厂、泛型
 *
 */
public class FragmentFactory {

	private FragmentActivity mContext;
	private static FragmentFactory factory = new FragmentFactory();
	//用于存储已创建的Fragment对象
	private Map<String, Fragment> mFragmentMap=new HashMap<>();
	private int mLayoutId;

	private FragmentFactory() {
	}

	public static FragmentFactory getInstance() {
		return factory;
	}

	//layoutId 传入布局文件的id
	public FragmentFactory init(FragmentActivity context,int layoutId) {
		this.mContext = context;
		this.mLayoutId=layoutId;
		return factory;
	}

	public Activity getParentActivity() {
		return mContext;
	}

	
	private <T extends Fragment> Fragment createFragment(Class<T> clazz) {
		Fragment fragment = null;
		try {
			fragment = getFragment(clazz.getName());
			FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction();
			hideFragment(fragmentTransaction);
			if (fragment == null) {

				fragment = (Fragment) clazz.newInstance();
				setFragment(fragment);
				fragmentTransaction.add(mLayoutId, fragment);
				fragmentTransaction.commit();
			} else {
				fragmentTransaction.show(fragment);
				fragmentTransaction.commit();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return fragment;
	}

	private <T extends Fragment> Fragment getFragment(String className) {
		Fragment fragment = mFragmentMap.get(className);
		return fragment;
	}

	private <T extends Fragment> void setFragment(Fragment fragment) throws InstantiationException, IllegalAccessException {
		String className = fragment.getClass().getName();
		mFragmentMap.put(className, fragment);
	}

	private void hideFragment(FragmentTransaction fragmentTransaction) {
		Set<String> keySet = mFragmentMap.keySet();
		for (String key : keySet) {
			Fragment fragment = mFragmentMap.get(key);
			fragmentTransaction.hide(fragment);
		}
		
	}
	
	public <T extends Fragment> T showFragment(Class<T> clazz) {
		return (T) createFragment(clazz);
	}

	
	public Fragment getCurrentShowFragment() {
		List<Fragment> fragments = mContext.getSupportFragmentManager().getFragments();
		if(fragments!=null){
			for (Fragment fragment : fragments) {
				if (fragment != null) {
					if (fragment.isVisible()) {
						return fragment;
					}
				}
			}
		}
		return null;
	}
	
	public void removeFragments(){
        FragmentTransaction fragmentTransaction=mContext.getSupportFragmentManager().beginTransaction();
		Iterator<String> iterator = mFragmentMap.keySet().iterator();
		while (iterator.hasNext()) {
			Fragment fragment = mFragmentMap.get(iterator.next());
			fragmentTransaction.remove(fragment);
			iterator.remove();;
		}
	}

}
