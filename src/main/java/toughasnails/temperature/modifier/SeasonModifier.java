/*******************************************************************************
 * Copyright 2016, the Biomes O' Plenty Team
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/
package toughasnails.temperature.modifier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import toughasnails.api.config.SeasonsOption;
import toughasnails.api.config.SyncedConfig;
import toughasnails.api.season.Season.SubSeason;
import toughasnails.api.season.SeasonHelper;
import toughasnails.api.temperature.Temperature;
import toughasnails.api.config.GameplayOption;
import toughasnails.temperature.TemperatureDebugger;
import toughasnails.temperature.TemperatureDebugger.Modifier;

public class SeasonModifier extends TemperatureModifier
{
    public SeasonModifier(TemperatureDebugger debugger) 
    {
        super(debugger);
    }

    @Override
    public Temperature modifyTarget(World world, EntityPlayer player, Temperature temperature) 
    {
        int temperatureLevel = temperature.getRawValue();
        SubSeason season = SeasonHelper.getSeasonData(world).getSubSeason();
        
        if (!(SyncedConfig.getBooleanValue(SeasonsOption.ENABLE_SEASONS)))
        {
        	season = SubSeason.MID_SUMMER;
        }
        
        debugger.start(Modifier.SEASON_TARGET, temperatureLevel);
        
        if (world.provider.isSurfaceWorld())
        {
	        switch (season)
	        {
	        case EARLY_SPRING:
	            temperatureLevel -= 5;
                break;
                
            //MID_SPRING = 0
                
	        case LATE_SPRING:
	            temperatureLevel += 2;
                break;
                
	        case EARLY_SUMMER:
	            temperatureLevel += 4;
	            break;
	            
	        case MID_SUMMER:
	            temperatureLevel += 6;
	            break;
	            
	        case LATE_SUMMER:
	            temperatureLevel += 4;
	            break;
	            
	        case EARLY_AUTUMN:
	            temperatureLevel += 2;
	            break;
	            
	        //MID_AUTUMN = 0;
	            
	        case LATE_AUTUMN:
	            temperatureLevel -= 3;
	            break;
	            
	        case EARLY_WINTER:
	            temperatureLevel -= 7;
	            break;
	            
	        case MID_WINTER:
	            temperatureLevel -= 15;
	            break;
	            
	        case LATE_WINTER:
                temperatureLevel -= 10;
                break;
	            
	        default:
	            break;
	        }
        }
        debugger.end(temperatureLevel);
        
        return new Temperature(temperatureLevel);
    }

}
