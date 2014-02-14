/* Copyright (c) 2013 Pozirk Games
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

package com.pozirk.ads.samads
{
	import flash.events.EventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	import flash.system.Capabilities;
	
	/**
	 * Main Samsung Ads class
	 * @author Pozirk Games (http://www.pozirk.com)
	 */
	public class SamAds extends EventDispatcher
	{
		protected var _ctx:ExtensionContext;

		public function SamAds()
		{
			_ctx = ExtensionContext.createExtensionContext("com.pozirk.ads.SamAds", null);
			if(_ctx != null)
				_ctx.addEventListener(StatusEvent.STATUS, onStatus);
			else
				trace("Error! ANE file was not properly added your project.");
		}

		public function init(invBannerID:String, invInterID:String = ""):void
		{
			_ctx.call("init", invBannerID, invInterID);
		}
		
		/**
		 * Show ad
		 * @param	size - one of the constants from AdParams
		 * @param	halign - left, center, right,  from AdParams
		 * @param	valign - from AdParams
		 * @param	testDevice - device ID, one of the  way to find it is to run ads in regular mode and find the following line in logcat: <To get test ads on this device, call adRequest.addTestDevice("XXX");>. Just pass XXX as testDevice.
		 */
		public function show(size:int, halign:int, valign:int, testDevice:String = null):void
		{
			_ctx.call("show", size, halign, valign, testDevice);
		}

		public function hide():void
		{
			_ctx.call("hide");
		}

		/**
		 * Show interstitial ad, if it is not cached yet, nothing will be shown
		 */
		public function showInterstitial():void
		{
			_ctx.call("showInterstitial");
		}
		
		protected function onStatus(event:StatusEvent):void
		{
			var e:AdEvent = null;
			//trace(event.code+event.level);
			switch(event.code)
			{
				case "INIT_OK":
				{
					e = new AdEvent(AdEvent.INIT_OK);
					break;
				}
				
				case "INIT_FAIL":
				{
					e = new AdEvent(AdEvent.INIT_FAIL, event.level);
					break;
				}
				
				case "AD_SHOW_OK":
				{
					e = new AdEvent(AdEvent.AD_SHOW_OK, event.level);
					break;
				}
				
				case "AD_SHOW_FAIL":
				{
					e = new AdEvent(AdEvent.AD_SHOW_FAIL, event.level);
					break;
				}
				
				case "INTERSTITIAL_FAIL":
				{
					e = new AdEvent(AdEvent.INTERSTITIAL_FAIL, event.level);
					break;
				}
			}
			
			if(e != null)
				this.dispatchEvent(e);
		}

		public function dispose():void
		{
			_ctx.call("dispose");
			_ctx.dispose();
		}
	}
}
