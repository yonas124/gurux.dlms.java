//
// --------------------------------------------------------------------------
//  Gurux Ltd
// 
//
//
// Filename:        $HeadURL$
//
// Version:         $Revision$,
//                  $Date$
//                  $Author$
//
// Copyright (c) Gurux Ltd
//
//---------------------------------------------------------------------------
//
//  DESCRIPTION
//
// This file is a part of Gurux Device Framework.
//
// Gurux Device Framework is Open Source software; you can redistribute it
// and/or modify it under the terms of the GNU General Public License 
// as published by the Free Software Foundation; version 2 of the License.
// Gurux Device Framework is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of 
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
// See the GNU General Public License for more details.
//
// More information of Gurux products: http://www.gurux.org
//
// This code is licensed under the GNU General Public License v2. 
// Full text may be retrieved at http://www.gnu.org/licenses/gpl-2.0.txt
//---------------------------------------------------------------------------

package gurux.dlms.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import gurux.dlms.GXByteBuffer;
import gurux.dlms.GXDLMSClient;
import gurux.dlms.GXDLMSSettings;
import gurux.dlms.GXDateTime;
import gurux.dlms.enums.DataType;
import gurux.dlms.enums.ObjectType;
import gurux.dlms.internal.GXCommon;

public class GXDLMSActivityCalendar extends GXDLMSObject
        implements IGXDLMSBase {
    private String calendarNameActive;
    private String calendarNamePassive;
    private GXDLMSSeasonProfile[] seasonProfileActive;
    private GXDLMSWeekProfile[] weekProfileTableActive;
    private GXDLMSDayProfile[] dayProfileTableActive;
    private GXDLMSSeasonProfile[] seasonProfilePassive;
    private GXDLMSDayProfile[] dayProfileTablePassive;
    private GXDLMSWeekProfile[] weekProfileTablePassive;
    private GXDateTime time;

    /**
     * Constructor.
     */
    public GXDLMSActivityCalendar() {
        this("0.0.13.0.0.255");
    }

    /**
     * Constructor.
     * 
     * @param ln
     *            Logical Name of the object.
     */
    public GXDLMSActivityCalendar(final String ln) {
        this(ln, (short) 0);
    }

    /**
     * Constructor.
     * 
     * @param ln
     *            Logical Name of the object.
     * @param sn
     *            Short Name of the object.
     */
    public GXDLMSActivityCalendar(final String ln, final int sn) {
        super(ObjectType.ACTIVITY_CALENDAR, ln, sn);
    }

    public final String getCalendarNameActive() {
        return calendarNameActive;
    }

    public final void setCalendarNameActive(final String value) {
        calendarNameActive = value;
    }

    public final GXDLMSSeasonProfile[] getSeasonProfileActive() {
        return seasonProfileActive;
    }

    public final void
            setSeasonProfileActive(final GXDLMSSeasonProfile[] value) {
        seasonProfileActive = value;
    }

    public final GXDLMSWeekProfile[] getWeekProfileTableActive() {
        return weekProfileTableActive;
    }

    public final void
            setWeekProfileTableActive(final GXDLMSWeekProfile[] value) {
        weekProfileTableActive = value;
    }

    public final GXDLMSDayProfile[] getDayProfileTableActive() {
        return dayProfileTableActive;
    }

    public final void setDayProfileTableActive(final GXDLMSDayProfile[] value) {
        dayProfileTableActive = value;
    }

    public final String getCalendarNamePassive() {
        return calendarNamePassive;
    }

    public final void setCalendarNamePassive(final String value) {
        calendarNamePassive = value;
    }

    public final GXDLMSSeasonProfile[] getSeasonProfilePassive() {
        return seasonProfilePassive;
    }

    public final void
            setSeasonProfilePassive(final GXDLMSSeasonProfile[] value) {
        seasonProfilePassive = value;
    }

    public final GXDLMSWeekProfile[] getWeekProfileTablePassive() {
        return weekProfileTablePassive;
    }

    public final void
            setWeekProfileTablePassive(final GXDLMSWeekProfile[] value) {
        weekProfileTablePassive = value;
    }

    public final GXDLMSDayProfile[] getDayProfileTablePassive() {
        return dayProfileTablePassive;
    }

    public final void
            setDayProfileTablePassive(final GXDLMSDayProfile[] value) {
        dayProfileTablePassive = value;
    }

    public final GXDateTime getTime() {
        return time;
    }

    public final void setTime(final GXDateTime value) {
        time = value;
    }

    @Override
    public final Object[] getValues() {
        return new Object[] { getLogicalName(), getCalendarNameActive(),
                getSeasonProfileActive(), getWeekProfileTableActive(),
                getDayProfileTableActive(), getCalendarNamePassive(),
                getSeasonProfilePassive(), getWeekProfileTablePassive(),
                getDayProfileTablePassive(), getTime() };
    }

    /*
     * Returns collection of attributes to read. If attribute is static and
     * already read or device is returned HW error it is not returned.
     */
    @Override
    public final int[] getAttributeIndexToRead() {
        final java.util.ArrayList<Integer> attributes =
                new java.util.ArrayList<Integer>();
        // LN is static and read only once.
        if (getLogicalName() == null || getLogicalName().compareTo("") == 0) {
            attributes.add(1);
        }
        // CalendarNameActive
        if (canRead(2)) {
            attributes.add(2);
        }
        // SeasonProfileActive
        if (canRead(3)) {
            attributes.add(3);
        }

        // WeekProfileTableActive
        if (canRead(4)) {
            attributes.add(4);
        }
        // DayProfileTableActive
        if (canRead(5)) {
            attributes.add(5);
        }
        // CalendarNamePassive
        if (canRead(6)) {
            attributes.add(6);
        }
        // SeasonProfilePassive
        if (canRead(7)) {
            attributes.add(7);
        }
        // WeekProfileTablePassive
        if (canRead(8)) {
            attributes.add(8);
        }
        // DayProfileTablePassive
        if (canRead(9)) {
            attributes.add(9);
        }
        // Time.
        if (canRead(10)) {
            attributes.add(10);
        }
        return GXDLMSObjectHelpers.toIntArray(attributes);
    }

    /*
     * Returns amount of attributes.
     */
    @Override
    public final int getAttributeCount() {
        return 10;
    }

    /*
     * Returns amount of methods.
     */
    @Override
    public final int getMethodCount() {
        return 1;
    }

    @Override
    public final DataType getDataType(final int index) {
        if (index == 1) {
            return DataType.OCTET_STRING;
        }
        if (index == 2) {
            return DataType.OCTET_STRING;
        }
        if (index == 3) {
            return DataType.ARRAY;
        }
        if (index == 4) {
            return DataType.ARRAY;
        }
        if (index == 5) {
            return DataType.ARRAY;
        }
        if (index == 6) {
            return DataType.OCTET_STRING;
        }
        if (index == 7) {
            return DataType.ARRAY;
        }
        if (index == 8) {
            return DataType.ARRAY;
        }
        if (index == 9) {
            return DataType.ARRAY;
        }
        if (index == 10) {
            return DataType.OCTET_STRING;
        }
        throw new IllegalArgumentException(
                "getDataType failed. Invalid attribute index.");
    }

    static Object getSeasonProfile(final GXDLMSSeasonProfile[] target) {
        GXByteBuffer data = new GXByteBuffer();
        data.setUInt8(DataType.ARRAY.getValue());
        if (target == null) {
            // Add count
            GXCommon.setObjectCount(0, data);
        } else {
            int cnt = target.length;
            // Add count
            GXCommon.setObjectCount(cnt, data);
            for (final GXDLMSSeasonProfile it : target) {
                data.setUInt8(DataType.STRUCTURE.getValue());
                data.setUInt8(3);
                GXCommon.setData(data, DataType.OCTET_STRING,
                        GXCommon.getBytes(it.getName()));
                GXCommon.setData(data, DataType.OCTET_STRING, it.getStart());
                GXCommon.setData(data, DataType.OCTET_STRING,
                        GXCommon.getBytes(it.getWeekName()));
            }
        }
        return data.array();
    }

    static Object getWeekProfileTable(final GXDLMSWeekProfile[] target) {
        GXByteBuffer data = new GXByteBuffer();
        data.setUInt8(DataType.ARRAY.getValue());
        if (target == null) {
            // Add count
            GXCommon.setObjectCount(0, data);
        } else {
            // Add count
            GXCommon.setObjectCount(target.length, data);
            for (final GXDLMSWeekProfile it : target) {
                data.setUInt8(DataType.ARRAY.getValue());
                data.setUInt8(8);
                GXCommon.setData(data, DataType.OCTET_STRING,
                        GXCommon.getBytes(it.getName()));
                GXCommon.setData(data, DataType.UINT8, it.getMonday());
                GXCommon.setData(data, DataType.UINT8, it.getTuesday());
                GXCommon.setData(data, DataType.UINT8, it.getWednesday());
                GXCommon.setData(data, DataType.UINT8, it.getThursday());
                GXCommon.setData(data, DataType.UINT8, it.getFriday());
                GXCommon.setData(data, DataType.UINT8, it.getSaturday());
                GXCommon.setData(data, DataType.UINT8, it.getSunday());
            }
        }
        return data.array();
    }

    static Object getDayProfileTable(final GXDLMSDayProfile[] target) {
        GXByteBuffer data = new GXByteBuffer();
        data.setUInt8(DataType.ARRAY.getValue());
        if (target == null) {
            // Add count
            GXCommon.setObjectCount(0, data);
        } else {
            // Add count
            GXCommon.setObjectCount(target.length, data);
            for (final GXDLMSDayProfile it : target) {
                data.setUInt8(DataType.STRUCTURE.getValue());
                data.setUInt8(2);
                GXCommon.setData(data, DataType.UINT8, it.getDayId());
                data.setUInt8(DataType.ARRAY.getValue());
                // Add count
                GXCommon.setObjectCount(it.getDaySchedules().length, data);
                for (final GXDLMSDayProfileAction action : it
                        .getDaySchedules()) {
                    data.setUInt8(DataType.STRUCTURE.getValue());
                    data.setUInt8(3);
                    GXCommon.setData(data, DataType.TIME,
                            action.getStartTime());
                    GXCommon.setData(data, DataType.OCTET_STRING,
                            GXCommon.getBytes(action.getScriptLogicalName()));
                    GXCommon.setData(data, DataType.UINT16,
                            action.getScriptSelector());
                }
            }
        }
        return data.array();
    }

    /*
     * Returns value of given attribute.
     */
    @Override
    public final Object getValue(final GXDLMSSettings settings, final int index,
            final int selector, final Object parameters) {
        switch (index) {
        case 1:
            return getLogicalName();
        case 2:
            return GXDLMSClient.changeType(
                    GXCommon.getBytes(getCalendarNameActive()),
                    DataType.OCTET_STRING);
        case 3:
            return getSeasonProfile(seasonProfileActive);
        case 4:
            return getWeekProfileTable(weekProfileTableActive);
        case 5:
            return getDayProfileTable(dayProfileTableActive);
        case 6:
            return GXDLMSClient.changeType(
                    GXCommon.getBytes(getCalendarNamePassive()),
                    DataType.OCTET_STRING);
        case 7:
            return getSeasonProfile(seasonProfilePassive);
        case 8:
            return getWeekProfileTable(weekProfileTablePassive);
        case 9:
            return getDayProfileTable(dayProfileTablePassive);
        case 10:
            return getTime();
        default:
            throw new IllegalArgumentException(
                    "GetValue failed. Invalid attribute index.");
        }
    }

    static GXDLMSSeasonProfile[] getSeasonProfile(final Object value) {
        final List<GXDLMSSeasonProfile> items =
                new ArrayList<GXDLMSSeasonProfile>();
        if (value != null) {
            for (final Object item : (Object[]) value) {
                final GXDLMSSeasonProfile it = new GXDLMSSeasonProfile();
                it.setName(GXDLMSClient.changeType((byte[]) Array.get(item, 0),
                        DataType.STRING).toString());
                it.setStart((GXDateTime) GXDLMSClient.changeType(
                        (byte[]) Array.get(item, 1), DataType.DATETIME));
                it.setWeekName(
                        GXDLMSClient.changeType((byte[]) Array.get(item, 2),
                                DataType.STRING).toString());
                items.add(it);
            }
        }
        return items.toArray(new GXDLMSSeasonProfile[items.size()]);
    }

    static GXDLMSWeekProfile[] getWeekProfileTable(final Object value) {
        final List<GXDLMSWeekProfile> items =
                new ArrayList<GXDLMSWeekProfile>();
        if (value != null) {
            for (final Object item : (Object[]) value) {
                final GXDLMSWeekProfile it = new GXDLMSWeekProfile();
                it.setName(GXDLMSClient.changeType((byte[]) Array.get(item, 0),
                        DataType.STRING).toString());
                it.setMonday(((Number) Array.get(item, 1)).intValue());
                it.setTuesday(((Number) Array.get(item, 2)).intValue());
                it.setWednesday(((Number) Array.get(item, 3)).intValue());
                it.setThursday(((Number) Array.get(item, 4)).intValue());
                it.setFriday(((Number) Array.get(item, 5)).intValue());
                it.setSaturday(((Number) Array.get(item, 6)).intValue());
                it.setSunday(((Number) Array.get(item, 7)).intValue());
                items.add(it);
            }
        }
        return items.toArray(new GXDLMSWeekProfile[items.size()]);
    }

    static GXDLMSDayProfile[] getDayProfileTable(final Object value) {
        final List<GXDLMSDayProfile> items = new ArrayList<GXDLMSDayProfile>();
        if (value != null) {
            for (final Object item : (Object[]) value) {
                final GXDLMSDayProfile it = new GXDLMSDayProfile();
                it.setDayId(((Number) Array.get(item, 0)).intValue());
                final List<GXDLMSDayProfileAction> actions =
                        new ArrayList<GXDLMSDayProfileAction>();
                for (final Object it2 : (Object[]) Array.get(item, 1)) {
                    final GXDLMSDayProfileAction ac =
                            new GXDLMSDayProfileAction();
                    ac.setStartTime((GXDateTime) GXDLMSClient.changeType(
                            (byte[]) Array.get(it2, 0), DataType.TIME));
                    ac.setScriptLogicalName(
                            GXDLMSClient.changeType((byte[]) Array.get(it2, 1),
                                    DataType.OCTET_STRING).toString());
                    ac.setScriptSelector(
                            ((Number) Array.get(it2, 2)).intValue());
                    actions.add(ac);
                }
                it.setDaySchedules(actions
                        .toArray(new GXDLMSDayProfileAction[actions.size()]));
                items.add(it);
            }
        }
        return items.toArray(new GXDLMSDayProfile[items.size()]);
    }

    /*
     * Set value of given attribute.
     */
    @Override
    public final void setValue(final GXDLMSSettings settings, final int index,
            final Object value) {
        switch (index) {
        case 1:
            super.setValue(settings, index, value);
            break;
        case 2:
            setCalendarNameActive(GXDLMSClient
                    .changeType((byte[]) value, DataType.STRING).toString());
            break;
        case 3:
            setSeasonProfileActive(getSeasonProfile(value));
            break;
        case 4:
            setWeekProfileTableActive(getWeekProfileTable(value));
            break;
        case 5:
            setDayProfileTableActive(getDayProfileTable(value));
            break;
        case 6:
            setCalendarNamePassive(GXDLMSClient
                    .changeType((byte[]) value, DataType.STRING).toString());
            break;
        case 7:
            setSeasonProfilePassive(getSeasonProfile(value));
            break;
        case 8:
            setWeekProfileTablePassive(getWeekProfileTable(value));
            break;
        case 9:
            setDayProfileTablePassive(getDayProfileTable(value));
            break;
        case 10:
            if (value instanceof GXDateTime) {
                setTime((GXDateTime) value);
            } else {
                setTime((GXDateTime) GXDLMSClient.changeType((byte[]) value,
                        DataType.DATETIME));
            }
            break;
        default:
            throw new IllegalArgumentException(
                    "GetValue failed. Invalid attribute index.");
        }
    }
}