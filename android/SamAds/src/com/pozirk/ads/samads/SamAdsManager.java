/* Copyright (c) 2014 Pozirk Games
 * http://www.pozirk.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pozirk.ads.samads;

import android.app.Activity;
//import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.sec.android.ad.*;
import com.sec.android.ad.info.*;

public class SamAdsManager implements AdNotificationListener, AdInterstitialListener
{
  protected String _invBannerID, _invInterID;
  protected AdHubView _adView = null;
  protected AdSize _adSize = AdSize.BANNER_320x50;
  protected RelativeLayout _parentView;
  protected Activity _act;
  protected ExtensionContext _ctx;
  protected AdHubInterstitial _interstitial;

  public SamAdsManager(String invBannerID, String invInterID, Activity act, ExtensionContext ctx)
  {
	  _act = act;
	  _ctx = ctx;

	  RelativeLayout layout = new RelativeLayout(_act);
	  _act.addContentView(layout, new ViewGroup.LayoutParams(-1, -1));

	  _invBannerID = invBannerID;
	  _invInterID = invInterID;

	  _parentView = layout;
  }
  
  public void show(int size, int halign, int valign, String testDevice)
  {
  	hide();
  	
  	switch(size)
  	{
  	case 1: _adSize = AdSize.BANNER_320x50; break; //set by default, but leave it here for reference
  	case 2: _adSize = AdSize.BANNER_640x100; break;
  	case 3: _adSize = AdSize.TABLET_300x250; break;
  	case 4: _adSize = AdSize.TABLET_728x90; break;
  	//case 6: _adSize = AdSize.IAB_WIDE_SKYSCRAPER; break;
  	}
  	
  	_adView = new AdHubView(_act, _invBannerID, _adSize);

  	_adView.setListener(this);
  	
  	_adView.startAd();

  	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
  	params.addRule(halign, -1);
  	params.addRule(valign, -1);
  	_parentView.addView(_adView, params);
  }

  public void hide()
  {
  	if(_adView != null)
  	{
  		_adView.stopAd();
  		_parentView.removeView(_adView);
  	}
  	
  	//_adView.destroy(); [update: ???]
  	_adView = null;
  }

  public void showInterstitial()
  {
  	_interstitial = new AdHubInterstitial(_act, _invInterID);
  	
  	_interstitial.setListener(this);
  	_interstitial.startAd();
  }

  public void dispose()
  {
  	hide();
  }

  public void onAdReceived(AdHubView ad)
  {
  	if(_ctx != null)
    {
  		if(_adView != null)
  			_ctx.dispatchStatusEventAsync("AD_SHOW_OK", String.valueOf(_adSize.getWidth()) + "x" + String.valueOf(_adSize.getHeight()));
      else
      	_ctx.dispatchStatusEventAsync("AD_SHOW_FAIL", "Error! No AdView.");
    }
  }

  public void onAdFailed(AdHubView ad, Exception exc)
  {
  	if(_ctx != null)
  		_ctx.dispatchStatusEventAsync("AD_SHOW_FAIL", exc.getMessage());
  }

  public void onAdInterstitialReceived(AdHubInterstitial ad)
  {
  	if(_ctx != null)
  		_ctx.dispatchStatusEventAsync("AD_SHOW_OK", "Interestial");
  }

  public void onAdInterstitialFailed(AdHubInterstitial ad, Exception exc)
  {
  	if(_ctx != null)
  		_ctx.dispatchStatusEventAsync("AD_SHOW_FAIL", exc.getMessage());
  }

  public void onAdInterstitialClosed(AdHubInterstitial ad)
  {
  	if(_ctx != null)
  		_ctx.dispatchStatusEventAsync("INTERSTITIAL_CLOSED", "");
  }
}