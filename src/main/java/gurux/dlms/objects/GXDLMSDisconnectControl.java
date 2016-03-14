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

import gurux.dlms.GXDLMSClient;
import gurux.dlms.GXDLMSSettings;
import gurux.dlms.enums.DataType;
import gurux.dlms.enums.ObjectType;
import gurux.dlms.objects.enums.ControlMode;
import gurux.dlms.objects.enums.ControlState;

public class GXDLMSDisconnectControl extends GXDLMSObject
        implements IGXDLMSBase {
    private boolean outputState;
    private ControlState controlState;
    private ControlMode controlMode;

    /**
     * Constructor.
     */
    public GXDLMSDisconnectControl() {
        super(ObjectType.DISCONNECT_CONTROL);
    }

    /**
     * Constructor.
     * 
     * @param ln
     *            Logical Name of the object.
     */
    public GXDLMSDisconnectControl(final String ln) {
        super(ObjectType.DISCONNECT_CONTROL, ln, 0);
    }

    /**
     * Constructor.
     * 
     * @param ln
     *            Logical Name of the object.
     * @param sn
     *            Short Name of the object.
     */
    public GXDLMSDisconnectControl(final String ln, final int sn) {
        super(ObjectType.DISCONNECT_CONTROL, ln, sn);
    }

    /**
     * @return Output state of COSEM Disconnect Control object.
     */
    public final boolean getOutputState() {
        return outputState;
    }

    /**
     * @param value
     *            Output state of COSEM Disconnect Control object.
     */
    public final void setOutputState(final boolean value) {
        outputState = value;
    }

    /**
     * @return Control state of COSEM Disconnect Control object.
     */
    public final ControlState getControlState() {
        return controlState;
    }

    /**
     * @param value
     *            Control state of COSEM Disconnect Control object.
     */
    public final void setControlState(final ControlState value) {
        controlState = value;
    }

    /**
     * @return Control mode of COSEM Disconnect Control object.
     */
    public final ControlMode getControlMode() {
        return controlMode;
    }

    /**
     * @param value
     *            Control mode of COSEM Disconnect Control object.
     */
    public final void setControlMode(final ControlMode value) {
        controlMode = value;
    }

    /*
     * Forces the disconnect control object into 'disconnected' state if remote
     * disconnection is enabled (control mode > 0).
     */
    public final byte[][] remoteDisconnect(final GXDLMSClient client) {
        return client.method(this, 1, 0, DataType.UINT8);
    }

    /*
     * Forces the disconnect control object into the 'ready_for_reconnection'
     * state if a direct remote reconnection is disabled (control_mode = 1, 3,
     * 5, 6). Forces the disconnect control object into the 'connected' state if
     * a direct remote reconnection is enabled (control_mode = 2, 4).
     */
    public final byte[][] remoteReconnect(final GXDLMSClient client) {
        return client.method(this, 2, 0, DataType.UINT8);
    }

    @Override
    public final Object[] getValues() {
        return new Object[] { getLogicalName(), getOutputState(),
                getControlState(), getControlMode() };
    }

    @Override
    public final int[] getAttributeIndexToRead() {
        java.util.ArrayList<Integer> attributes =
                new java.util.ArrayList<Integer>();
        // LN is static and read only once.
        if (getLogicalName() == null || getLogicalName().compareTo("") == 0) {
            attributes.add(1);
        }
        // OutputState
        if (canRead(2)) {
            attributes.add(2);
        }
        // ControlState
        if (canRead(3)) {
            attributes.add(3);
        }
        // ControlMode
        if (canRead(4)) {
            attributes.add(4);
        }
        return GXDLMSObjectHelpers.toIntArray(attributes);
    }

    @Override
    public final int getAttributeCount() {
        return 4;
    }

    @Override
    public final int getMethodCount() {
        return 2;
    }

    @Override
    public final DataType getDataType(final int index) {
        if (index == 1) {
            return DataType.OCTET_STRING;
        }
        if (index == 2) {
            return DataType.BOOLEAN;
        }
        if (index == 3) {
            return DataType.ENUM;
        }
        if (index == 4) {
            return DataType.ENUM;
        }
        throw new IllegalArgumentException(
                "getDataType failed. Invalid attribute index.");
    }

    @Override
    public final Object getValue(final GXDLMSSettings settings, final int index,
            final int selector, final Object parameters) {
        if (index == 1) {
            return getLogicalName();
        }
        if (index == 2) {
            return getOutputState();
        }
        if (index == 3) {
            return getControlState().ordinal();
        }
        if (index == 4) {
            return getControlMode().ordinal();
        }
        throw new IllegalArgumentException(
                "GetValue failed. Invalid attribute index.");
    }

    @Override
    public final void setValue(final GXDLMSSettings settings, final int index,
            final Object value) {
        if (index == 1) {
            if (value != null) {
                super.setValue(settings, index, value);
            }
        } else if (index == 2) {
            if (value == null) {
                setOutputState(false);
            } else {
                setOutputState((Boolean) value);
            }
        } else if (index == 3) {
            if (value == null) {
                setControlState(ControlState.DISCONNECTED);
            } else {
                setControlState(
                        ControlState.values()[((Number) value).intValue()]);
            }
        } else if (index == 4) {
            if (value == null) {
                setControlMode(ControlMode.NONE);
            } else {
                setControlMode(
                        ControlMode.values()[((Number) value).intValue()]);
            }

        } else {
            throw new IllegalArgumentException(
                    "GetValue failed. Invalid attribute index.");
        }
    }
}
