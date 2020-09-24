/*******************************************************************************
 * Copyright 2019 Cu Pham
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package jaist.tanlab.echonetIoTAgent.echonet.object.utils;

public enum eDeviceType {
	GasLeakSensor("GasLeakSensor"),
	CrimePreventionSensor("CrimePreventionSensor"),
	EmergencyButton("EmergencyButton"),
	FirstAidSensor("FirstAidSensor"),
	EarthquakeSensor("EarthquakeSensor"),
	ElectricLeakSensor ("ElectricLeakSensor"),
	HumanDetectionSensor ("HumanDetectionSensor"),
	VisitorDetectionSensor ("VisitorDetectionSensor"),
	CallSensor ("CallSensor"),
	CondensationSensor ("CondensationSensor"),
	AirPollutionSensor ("AirPollutionSensor"),
	OxygenSensor ("OxygenSensor"),
	IlluminanceSensor ("IlluminanceSensor"),
	SoundSensor ("SoundSensor"),
	MailingSensor ("MailingSensor"),
	WeightSensor ("WeightSensor"),
	TemperatureSensor ("TemperatureSensor"),
	HumiditySensor ("HumiditySensor"),
	RainSensor ("RainSensor"),
	WaterLevelSensor ("WaterLevelSensor"),
	BathWaterLevelSensor ("BathWaterLevelSensor"),
	BathHeatingStatusSensor ("BathHeatingStatusSensor"),
	WaterLeakSensor ("WaterLeakSensor"),
	WaterOverFlowSensor ("WaterOverFlowSensor"),
	FireSensor ("FireSensor"),
	CigaretteSmokeSensor ("CigaretteSmokeSensor"),
	CO2Sensor ("CO2Sensor"),
	GasSensor ("GasSensor"),
	VOCSensor ("VOCSensor"),
	DifferentialPressureSensor ("DifferentialPressureSensor"),
	AirSpeedSensor ("AirSpeedSensor"),
	OdorSensor ("OdorSensor"),
	FlameSensor ("FlameSensor"),
	ElectricEnergySensor ("ElectricEnergySensor"),
	CurrentValueSensor ("CurrentValueSensor"),
	WaterFlowRateSensor ("WaterFlowRateSensor"),
	MicroMotionSensor ("MicroMotionSensor"),
	PassageSensor ("PassageSensor"),
	BedPresenceSensor ("BedPresenceSensor"),
	OpenCloseSensor ("OpenCloseSensor"),
	ActivityMountSensor ("ActivityMountSensor"),
	HumanBodyLocationSensor ("HumanBodyLocationSensor"),
	SnowSensor ("SnowSensor"),
	AirPressureSensor ("AirPressureSensor"),

	HomeAirconditioner ("HomeAirconditioner"),
	VentilationFan ("VentilationFan"),
	AirconditionerVentilationFan ("AirconditionerVentilationFan"),
	AirCleaner ("AirCleaner"),
	Humidifier ("Humidifier"),
	ElectricHeater ("ElectricHeater"),
	FanHeater ("FanHeater"),
	ElectricStorageHeater ("ElectricStorageHeater"),
	CommercialAirconditionerIndoorUnit ("CommercialAirconditionerIndoorUnit"),
	CommercialAirconditionerOutdoorUnit ("CommercialAirconditionerOutdoorUnit"),
	GasHeatCommercialAirconditionerIndoorUnit ("GasHeatCommercialAirconditionerIndoorUnit"),
	GasHeatCommercialAirconditionerOutdoorUnit ("GasHeatCommercialAirconditionerOutdoorUnit"),

	ElectricBlind ("ElectricBlind"),
	ElectricShutter ("ElectricShutter"),
	ElectricCurtain ("ElectricCurtain"),
	ElectricRainSlidingDoor ("ElectricRainSlidingDoor"),
	ElectricGate ("ElectricGate"),
	ElectricWindow ("ElectricWindow"),
	ElectricEntranceDoor ("ElectricEntranceDoor"),
	GardenSprinkler ("GardenSprinkler"),
	ElectricWaterHeater ("ElectricWaterHeater"),
	BidetEquippedToilet ("BidetEquippedToilet"),
	ElectricLock ("ElectricLock"),
	InstantaneousWaterHeater ("InstantaneousWaterHeater"),
	BathRoomDryer ("BathRoomDryer"),
	HomeSolarPowerGeneration ("HomeSolarPowerGeneration"),
	ColdHotWaterHeatSourceEquipment ("ColdHotWaterHeatSourceEquipment"),
	FloorHeater ("FloorHeater"),

	FuelCell ("FuelCell"),
	StorageBattery ("StorageBattery"),
	ElectricVehicleChargerDischarger ("ElectricVehicleChargerDischarger"),
	EngineCogeneration ("EngineCogeneration"),
	ElectricEnergyMeter ("ElectricEnergyMeter"),
	WaterFlowMeter ("WaterFlowMeter"),
	GasMeter ("GasMeter"),
	LPGasMeter ("LPGasMeter"),
	DistributionPanelMetering ("DistributionPanelMetering"),
	LowVoltageSmartElectricEnergyMeter ("LowVoltageSmartElectricEnergyMeter"),
	SmartGasMeter ("SmartGasMeter"),
	HighVoltageSmartElectricEnergyMeter ("HighVoltageSmartElectricEnergyMeter"),
	KeroseneMeter ("KeroseneMeter"),
	SmartKeroseneMeter ("SmartKeroseneMeter"),
	GeneralLight ("GeneralLight"),
	SingleFunctionLighting ("SingleFunctionLighting"),
	LightingForSolidLightEmittingSource ("LightingForSolidLightEmittingSource"),
	Buzzer ("Buzzer"),
	ElectricVehicleCharger ("ElectricVehicleCharger"),
	HouseholdSmallWindTurbinePowerGeneration ("HouseholdSmallWindTurbinePowerGeneration"),
	ExtendedLightingSystem ("ExtendedLightingSystem"),
	MultipleInputPCS ("MultipleInputPCS"),

	HotWaterPot ("HotWaterPot"),
	Refrigerator ("Refrigerator"),
	MicrowaveOven ("MicrowaveOven"),
	CookingHeater ("CookingHeater"),
	RiceCooker ("RiceCooker"),
	WashingMachine ("WashingMachine"),
	ClothesDryer ("ClothesDryer"),
	CommercialShowcase ("CommercialShowcase"),
	WasherDryer ("WasherDryer"),
	CommercialShowcaseOutdoorUnit ("CommercialShowcaseOutdoorUnit"),

	WeighingMachine ("WeighingMachine"),

	Switch ("Switch"),
	Controller ("Controller"),
	DREventController ("DREventController"),
	ParallelProcessingCombinationTypePowerControl ("ParallelProcessingCombinationTypePowerControl"),

	Display ("Display"),
	Television ("Television"),
	Audio ("Audio"),
	NetworkCamera ("NetworkCamera"),

	Profile ("Profile"),
	SuperObject ("SuperObject");



	private String name;
	private eDeviceType(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

}
