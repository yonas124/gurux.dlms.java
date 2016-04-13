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

package gurux.dlms;

import java.security.InvalidParameterException;

import gurux.dlms.internal.GXCommon;

/**
 * GXDLMSSNSettings contains commands for retrieving and setting the Short Name
 * settings of the server, shortly said SN referencing support.
 */
public class GXDLMSSNSettings {
    /**
     * Settings.
     */
    private byte[] conformanceBlock;

    /**
     * Constructor.
     */
    GXDLMSSNSettings() {
        conformanceBlock = new byte[3];
    }

    /**
     * Constructor.
     */
    GXDLMSSNSettings(final byte[] value) {
        setConformanceBlock(value);
    }

    /**
     * Clear all bits.
     */
    public final void clear() {
        conformanceBlock[0] = 0;
        conformanceBlock[1] = 0;
        conformanceBlock[2] = 0;
    }

    final void copyTo(final GXDLMSSNSettings target) {
        target.conformanceBlock = this.conformanceBlock;
    }

    /**
     * @return Is read supported.
     */
    public final boolean getRead() {
        return GXCommon.getBits(conformanceBlock[0], 0x10);
    }

    /**
     * @param value
     *            Is read supported.
     */
    public final void setRead(final boolean value) {
        conformanceBlock[0] =
                GXCommon.setBits(conformanceBlock[0], 0x10, value);
    }

    /**
     * @return Is write supported.
     */
    public final boolean getWrite() {
        return GXCommon.getBits(conformanceBlock[0], 0x8);
    }

    /**
     * @param value
     *            Is write supported.
     */
    public final void setWrite(final boolean value) {
        conformanceBlock[0] = GXCommon.setBits(conformanceBlock[0], 0x8, value);
    }

    public final boolean getUnconfirmedWrite() {
        return GXCommon.getBits(conformanceBlock[0], 0x4);
    }

    public final void setUnconfirmedWrite(final boolean value) {
        conformanceBlock[0] = GXCommon.setBits(conformanceBlock[0], 0x4, value);
    }

    public final boolean getInformationReport() {
        return GXCommon.getBits(conformanceBlock[1], 0x1);
    }

    public final void setInformationReport(final boolean value) {
        conformanceBlock[1] = GXCommon.setBits(conformanceBlock[1], 0x1, value);
    }

    public final boolean getMultipleReferences() {
        return GXCommon.getBits(conformanceBlock[1], 0x2);
    }

    public final void setMultipleReferences(final boolean value) {
        conformanceBlock[1] = GXCommon.setBits(conformanceBlock[1], 0x2, value);
    }

    public final boolean getParameterizedAccess() {
        return GXCommon.getBits(conformanceBlock[2], 0x20);
    }

    public final void setParameterizedAccess(final boolean value) {
        conformanceBlock[2] =
                GXCommon.setBits(conformanceBlock[2], 0x20, value);
    }

    /**
     * @return the conformance block.
     */
    public final byte[] getConformanceBlock() {
        return conformanceBlock;
    }

    /**
     * @param value
     *            the conformance block to set
     */
    public final void setConformanceBlock(final byte[] value) {
        if (value == null || value.length != 3) {
            throw new InvalidParameterException("Invalid conformance block.");
        }
        conformanceBlock = value;
    }
}