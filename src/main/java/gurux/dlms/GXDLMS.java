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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import gurux.dlms.enums.Command;
import gurux.dlms.enums.DataType;
import gurux.dlms.enums.ErrorCode;
import gurux.dlms.enums.InterfaceType;
import gurux.dlms.enums.ObjectType;
import gurux.dlms.enums.Priority;
import gurux.dlms.enums.RequestTypes;
import gurux.dlms.enums.ServiceClass;
import gurux.dlms.enums.ServiceError;
import gurux.dlms.enums.StateError;
import gurux.dlms.internal.GXCommon;
import gurux.dlms.internal.GXDataInfo;
import gurux.dlms.objects.GXDLMSActionSchedule;
import gurux.dlms.objects.GXDLMSActivityCalendar;
import gurux.dlms.objects.GXDLMSAssociationLogicalName;
import gurux.dlms.objects.GXDLMSAssociationShortName;
import gurux.dlms.objects.GXDLMSAutoAnswer;
import gurux.dlms.objects.GXDLMSAutoConnect;
import gurux.dlms.objects.GXDLMSClock;
import gurux.dlms.objects.GXDLMSData;
import gurux.dlms.objects.GXDLMSDemandRegister;
import gurux.dlms.objects.GXDLMSDisconnectControl;
import gurux.dlms.objects.GXDLMSExtendedRegister;
import gurux.dlms.objects.GXDLMSGprsSetup;
import gurux.dlms.objects.GXDLMSHdlcSetup;
import gurux.dlms.objects.GXDLMSIECOpticalPortSetup;
import gurux.dlms.objects.GXDLMSIecTwistedPairSetup;
import gurux.dlms.objects.GXDLMSImageTransfer;
import gurux.dlms.objects.GXDLMSIp4Setup;
import gurux.dlms.objects.GXDLMSLimiter;
import gurux.dlms.objects.GXDLMSMBusClient;
import gurux.dlms.objects.GXDLMSMBusMasterPortSetup;
import gurux.dlms.objects.GXDLMSMBusSlavePortSetup;
import gurux.dlms.objects.GXDLMSMacAddressSetup;
import gurux.dlms.objects.GXDLMSMessageHandler;
import gurux.dlms.objects.GXDLMSModemConfiguration;
import gurux.dlms.objects.GXDLMSObject;
import gurux.dlms.objects.GXDLMSPppSetup;
import gurux.dlms.objects.GXDLMSProfileGeneric;
import gurux.dlms.objects.GXDLMSPushSetup;
import gurux.dlms.objects.GXDLMSRegister;
import gurux.dlms.objects.GXDLMSRegisterActivation;
import gurux.dlms.objects.GXDLMSRegisterMonitor;
import gurux.dlms.objects.GXDLMSSapAssignment;
import gurux.dlms.objects.GXDLMSSchedule;
import gurux.dlms.objects.GXDLMSScriptTable;
import gurux.dlms.objects.GXDLMSSecuritySetup;
import gurux.dlms.objects.GXDLMSSpecialDaysTable;
import gurux.dlms.objects.GXDLMSTcpUdpSetup;

/**
 * GXDLMS implements methods to communicate with DLMS/COSEM metering devices.
 */
abstract class GXDLMS {

    /**
     * Constructor.
     */
    private GXDLMS() {

    }

    static byte getInvokeIDPriority(final GXDLMSSettings settings) {
        byte value = 0;
        if (settings.getPriority() == Priority.HIGH) {
            value |= 0x80;
        }
        if (settings.getServiceClass() == ServiceClass.CONFIRMED) {
            value |= 0x40;
        }
        value |= settings.getInvokeID();
        return value;
    }

    static GXDLMSObject createObject(final ObjectType type) {
        // If IC is manufacturer specific or unknown.
        if (type == null) {
            return new GXDLMSObject();
        }
        switch (type) {
        case ACTION_SCHEDULE:
            return new GXDLMSActionSchedule();
        case ACTIVITY_CALENDAR:
            return new GXDLMSActivityCalendar();
        case ASSOCIATION_LOGICAL_NAME:
            return new GXDLMSAssociationLogicalName();
        case ASSOCIATION_SHORT_NAME:
            return new GXDLMSAssociationShortName();
        case AUTO_ANSWER:
            return new GXDLMSAutoAnswer();
        case AUTO_CONNECT:
            return new GXDLMSAutoConnect();
        case CLOCK:
            return new GXDLMSClock();
        case DATA:
            return new GXDLMSData();
        case DEMAND_REGISTER:
            return new GXDLMSDemandRegister();
        case MAC_ADDRESS_SETUP:
            return new GXDLMSMacAddressSetup();
        case EXTENDED_REGISTER:
            return new GXDLMSExtendedRegister();
        case GPRS_SETUP:
            return new GXDLMSGprsSetup();
        case IEC_HDLC_SETUP:
            return new GXDLMSHdlcSetup();
        case IEC_LOCAL_PORT_SETUP:
            return new GXDLMSIECOpticalPortSetup();
        case IEC_TWISTED_PAIR_SETUP:
            return new GXDLMSIecTwistedPairSetup();
        case IP4_SETUP:
            return new GXDLMSIp4Setup();
        case MBUS_SLAVE_PORT_SETUP:
            return new GXDLMSMBusSlavePortSetup();
        case IMAGE_TRANSFER:
            return new GXDLMSImageTransfer();
        case SECURITY_SETUP:
            return new GXDLMSSecuritySetup();
        case DISCONNECT_CONTROL:
            return new GXDLMSDisconnectControl();
        case LIMITER:
            return new GXDLMSLimiter();
        case MBUS_CLIENT:
            return new GXDLMSMBusClient();
        case MODEM_CONFIGURATION:
            return new GXDLMSModemConfiguration();
        case PPP_SETUP:
            return new GXDLMSPppSetup();
        case PROFILE_GENERIC:
            return new GXDLMSProfileGeneric();
        case REGISTER:
            return new GXDLMSRegister();
        case REGISTER_ACTIVATION:
            return new GXDLMSRegisterActivation();
        case REGISTER_MONITOR:
            return new GXDLMSRegisterMonitor();
        case REGISTER_TABLE:
            return new GXDLMSObject();
        case ZIG_BEE_SAS_STARTUP:
            return new GXDLMSObject();
        case ZIG_BEE_SAS_JOIN:
            return new GXDLMSObject();
        case SAP_ASSIGNMENT:
            return new GXDLMSSapAssignment();
        case SCHEDULE:
            return new GXDLMSSchedule();
        case SCRIPT_TABLE:
            return new GXDLMSScriptTable();
        case SMTP_SETUP:
            return new GXDLMSObject();
        case SPECIAL_DAYS_TABLE:
            return new GXDLMSSpecialDaysTable();
        case STATUS_MAPPING:
            return new GXDLMSObject();
        case TCP_UDP_SETUP:
            return new GXDLMSTcpUdpSetup();
        case ZIG_BEE_SAS_APS_FRAGMENTATION:
            return new GXDLMSObject();
        case UTILITY_TABLES:
            return new GXDLMSObject();
        case MBUS_MASTER_PORT_SETUP:
            return new GXDLMSMBusMasterPortSetup();
        case PUSH_SETUP:
            return new GXDLMSPushSetup();
        case MESSAGE_HANDLER:
            return new GXDLMSMessageHandler();
        default:
            return new GXDLMSObject();
        }
    }

    /**
     * Generates an acknowledgment message, with which the server is informed to
     * send next packets.
     * 
     * @param settings
     *            DLMS settings.
     * @param type
     *            Frame type
     * @return Acknowledgment message as byte array.
     */
    static byte[] receiverReady(final GXDLMSSettings settings,
            final RequestTypes type) {
        if (type == RequestTypes.NONE) {
            throw new InvalidParameterException(
                    "Invalid receiverReady RequestTypes parameter.");
        }
        // Get next frame.
        if ((type.getValue() & RequestTypes.FRAME.getValue()) != 0) {
            int id = settings.getReceiverReady();
            return splitToHdlcFrames(settings, id, null)[0];
        }
        // Get next block.
        GXByteBuffer bb = new GXByteBuffer(6);
        bb.setUInt32(settings.getBlockIndex());
        settings.increaseBlockIndex();
        if (settings.isServer()) {
            return splitPdu(settings, Command.GET_RESPONSE, 2, bb, ErrorCode.OK,
                    null).get(0)[0];
        }
        return splitPdu(settings, Command.GET_REQUEST, 2, bb, ErrorCode.OK,
                null).get(0)[0];
    }

    static String getDescription(final int errCode) {
        String str;
        switch (ErrorCode.forValue(errCode)) {
        case REJECTED:
            str = "Rejected";
            break;
        case INVALID_HDLC_REPLY:
            str = "Not a reply";
            break;
        case OK:
            str = "";
            break;
        case HARDWARE_FAULT:
            str = "Access Error : Device reports a hardware fault.";
            break;
        case TEMPORARY_FAILURE:
            str = "Access Error : Device reports a temporary failure.";
            break;
        case READ_WRITE_DENIED:
            str = "Access Error : Device reports Read-Write denied.";
            break;
        case UNDEFINED_OBJECT:
            str = "Access Error : Device reports a undefined object.";
            break;
        case INCONSISTENT_CLASS:
            str = "Access Error : "
                    + "Device reports a inconsistent Class or object.";
            break;
        case UNAVAILABLE_OBJECT:
            str = "Access Error : Device reports a unavailable object.";
            break;
        case UNMATCHED_TYPE:
            str = "Access Error : Device reports a unmatched type.";
            break;
        case ACCESS_VIOLATED:
            str = "Access Error : Device reports scope of access violated.";
            break;
        case DATA_BLOCK_UNAVAILABLE:
            str = "Access Error : Data Block Unavailable.";
            break;
        case LONG_GET_OR_READ_ABORTED:
            str = "Access Error : Long Get Or Read Aborted.";
            break;
        case NO_LONG_GET_OR_READ_IN_PROGRESS:
            str = "Access Error : No Long Get Or Read In Progress.";
            break;
        case LONG_SET_OR_WRITE_ABORTED:
            str = "Access Error : Long Set Or Write Aborted.";
            break;
        case NO_LONG_SET_OR_WRITE_IN_PROGRESS:
            str = "Access Error : No Long Set Or Write In Progress.";
            break;
        case DATA_BLOCK_NUMBER_INVALID:
            str = "Access Error : Data Block Number Invalid.";
            break;
        case OTHER_REASON:
            str = "Access Error : Other Reason.";
            break;
        default:
            str = "Access Error : Unknown error.";
            break;
        }
        return str;
    }

    /**
     * Reserved for internal use.
     */
    static void checkInit(final GXDLMSSettings settings) {
        if (settings.getClientAddress() == 0) {
            throw new IllegalArgumentException("Invalid Client Address.");
        }
        if (settings.getServerAddress() == 0) {
            throw new IllegalArgumentException("Invalid Server Address.");
        }
    }

    static void appedData(final GXDLMSObject obj, final int index,
            final GXByteBuffer bb, final Object value) {

        DataType tp = obj.getDataType(index);
        if (tp == DataType.ARRAY) {
            if (value instanceof byte[]) {
                if (tp != DataType.OCTET_STRING) {
                    bb.set((byte[]) value);
                    return;
                }
            }
        } else {
            if (tp == DataType.NONE) {
                tp = GXCommon.getValueType(value);
                // If data type is not defined for Date Time it is write as
                // octet string.
                if (tp == DataType.DATETIME) {
                    tp = DataType.OCTET_STRING;
                }
            }
        }
        GXCommon.setData(bb, tp, value);
    }

    /**
     * Get used glo message.
     * 
     * @param command
     *            Executed command.
     * @return Integer value of glo message.
     */
    private static int getGloMessage(final Command command) {
        Command cmd;
        switch (command) {
        case READ_REQUEST:
        case GET_REQUEST:
            cmd = Command.GLO_GET_REQUEST;
            break;
        case WRITE_REQUEST:
        case SET_REQUEST:
            cmd = Command.GLO_SET_REQUEST;
            break;
        case METHOD_REQUEST:
            cmd = Command.GLO_METHOD_REQUEST;
            break;
        case READ_RESPONSE:
        case GET_RESPONSE:
            cmd = Command.GLO_GET_RESPONSE;
            break;
        case WRITE_RESPONSE:
        case SET_RESPONSE:
            cmd = Command.GLO_SET_RESPONSE;
            break;
        case METHOD_RESPONSE:
            cmd = Command.GLO_METHOD_RESPONSE;
            break;
        default:
            throw new GXDLMSException("Invalid GLO command.");
        }
        return cmd.getValue();
    }

    /**
     * Split PDU to blocks and frames.
     * 
     * @param settings
     *            DLMS settings.
     * @param command
     *            Command.
     * @param commandParameter
     * @param data
     *            Data to send.
     * @param error
     *            Error number.
     * @param date
     *            Optional date time value.
     * @return List of frames to send.
     */
    static List<byte[][]> splitPdu(final GXDLMSSettings settings,
            final Command command, final int commandParameter,
            final GXByteBuffer data, final ErrorCode error, final Date date) {
        GXByteBuffer bb = new GXByteBuffer();
        List<byte[][]> list = new ArrayList<byte[][]>();
        // For SN there is no need to split data for blocks.
        if (!settings.getUseLogicalNameReferencing()) {
            byte[] tmp = getSnPdus(settings, data, command);
            // If Ciphering is used.
            if (settings.getCipher() != null && command != Command.AARQ
                    && command != Command.AARE) {
                bb.set(settings.getCipher().encrypt(getGloMessage(command),
                        settings.getCipher().getSystemTitle(), tmp));
            } else {
                bb.set(tmp);
            }
            if (settings.getInterfaceType() == InterfaceType.HDLC) {
                if (command == Command.AARQ) {
                    list.add(splitToHdlcFrames(settings, 0x10, bb));
                } else {
                    list.add(splitToHdlcFrames(settings, 0, bb));
                }
            } else {
                list.add(splitToWrapperFrames(settings, bb));
            }
        } else {
            List<byte[]> pdus = getLnPdus(settings, commandParameter, data,
                    command, error, date);
            for (byte[] it : pdus) {
                // If Ciphering is used.
                if (settings.getCipher() != null && command != Command.AARQ
                        && command != Command.AARE) {
                    bb.set(settings.getCipher().encrypt(getGloMessage(command),
                            settings.getCipher().getSystemTitle(), it));
                } else {
                    bb.set(it);
                }
                if (settings.getInterfaceType() == InterfaceType.HDLC) {
                    if (command == Command.AARQ) {
                        list.add(splitToHdlcFrames(settings, 0x10, bb));
                    } else {
                        list.add(splitToHdlcFrames(settings, 0, bb));
                    }
                } else {
                    list.add(splitToWrapperFrames(settings, bb));
                }
                bb.clear();
            }
        }
        return list;

    }

    /**
     * Returns true if executed command is reply.
     * 
     * @param cmd
     *            Executed command.
     * @return Is command reply.
     */
    private static boolean isReplyMessage(final Command cmd) {
        return cmd == Command.GET_RESPONSE || cmd == Command.SET_RESPONSE
                || cmd == Command.METHOD_RESPONSE;
    }

    private static List<byte[]> getLnPdus(final GXDLMSSettings settings,
            final int commandParameter, final GXByteBuffer buff,
            final Command cmd, final ErrorCode error, final Date date) {
        List<byte[]> arr = new ArrayList<byte[]>();
        GXByteBuffer bb;
        int len;
        if (buff == null) {
            len = 0;
        } else {
            len = settings.getMaxReceivePDUSize();
            if (settings.getInterfaceType() == InterfaceType.HDLC) {
                len -= 7;
            } else {
                // Wrapper header size is 12.
                len -= 12;
            }
        }
        bb = new GXByteBuffer();
        int index = settings.getBlockIndex() - 1;
        boolean multibleBlocks = buff.size() > len;
        do {
            // If last packet.
            if (buff.size() - buff.position() < len) {
                len = buff.size() - buff.position();
            }
            if (cmd != Command.AARQ && cmd != Command.AARE) {
                // Add command.
                bb.setUInt8(cmd.getValue());
                if (cmd == Command.PUSH) {
                    // Is last block
                    if (buff.position() + len < buff.size()) {
                        bb.setUInt8(0);
                    } else {
                        bb.setUInt8(0x80);
                    }
                    // Set block number sent.
                    bb.setUInt8(0);
                    // Set block number acknowledged
                    bb.setUInt8((byte) ++index);
                    // Add APU tag.
                    bb.setUInt8(0);
                    // Add Addl fields
                    bb.setUInt8(0);
                    // Add Data-Notification
                    bb.setUInt8(0x0F);
                    // Add Long-Invoke-Id-And-Priority
                    settings.increaseBlockIndex();
                    bb.setUInt32(settings.getBlockIndex());
                    // Add date time.
                    if (date == null || date == new Date(0)) {
                        bb.setUInt8(DataType.NONE.getValue());
                    } else {
                        GXCommon.setData(bb, DataType.DATETIME, date);
                    }
                } else if (multibleBlocks) {
                    // If all data is not fit to one PDU.
                    bb.setUInt8(2);
                    // Add Invoke Id And Priority.
                    bb.setUInt8(getInvokeIDPriority(settings));
                    // Is last packet.
                    if (buff.position() + len < buff.size()) {
                        bb.setUInt8(0);
                    } else {
                        bb.setUInt8(1);
                    }
                    // Block index.
                    bb.setUInt32(++index);
                    // Add error code if reply.
                    if (isReplyMessage(cmd)) {
                        bb.setUInt8(0);
                    }
                    // Block length.
                    GXCommon.setObjectCount(len, bb);
                } else {
                    bb.setUInt8(commandParameter);
                    // Add Invoke Id And Priority.
                    bb.setUInt8(getInvokeIDPriority(settings));
                    // Add error code if reply and not Get Response With List.
                    if (isReplyMessage(cmd) && !(cmd == Command.GET_RESPONSE
                            && commandParameter == 3)) {
                        bb.setUInt8(error.getValue());
                    }
                }
            }

            bb.set(buff.getData(), buff.position(), len);
            buff.position(buff.position() + len);
            arr.add(bb.array());
            bb.clear();
        } while (buff.position() < buff.size());
        return arr;
    }

    private static byte[] getSnPdus(final GXDLMSSettings settings,
            final GXByteBuffer buff, final Command cmd) {
        GXByteBuffer bb = new GXByteBuffer();
        if (buff != null && buff.size() != 0) {
            // Add command.
            if (cmd != Command.AARQ && cmd != Command.AARE) {
                bb.setUInt8(cmd.getValue());
            }
            // Add data.
            bb.set(buff.getData(), 0, buff.size());
        }
        return bb.array();
    }

    static Object getAddress(final long value, final int size) {
        if (size < 2 && value < 0x80) {
            return (byte) (value << 1 | 1);
        }
        if (size < 4 && value < 0x4000) {
            return (short) ((value & 0x3F80) << 2 | (value & 0x7F) << 1 | 1);
        }
        if (value < 0x10000000) {
            return (int) ((value & 0xFE00000) << 4 | (value & 0x1FC000) << 3
                    | (value & 0x3F80) << 2 | (value & 0x7F) << 1 | 1);
        }
        throw new IllegalArgumentException("Invalid address.");
    }

    /**
     * @param value
     * @param bb
     */
    private static byte[] getAddressBytes(final int value, final int size) {
        Object tmp = getAddress(value, size);
        GXByteBuffer bb = new GXByteBuffer();
        if (tmp instanceof Byte) {
            bb.setUInt8(((Byte) tmp).byteValue());
        } else if (tmp instanceof Short) {
            bb.setUInt16(((Short) tmp).intValue());
        } else if (tmp instanceof Integer) {
            bb.setUInt32(((Integer) tmp).intValue());
        } else {
            throw new IllegalArgumentException("Invalid address type.");
        }
        return bb.array();
    }

    /**
     * Split DLMS PDU to HDLC frames.
     * 
     * @param settings
     *            DLMS settings.
     * @param frame
     *            Frame ID. If zero new is generated.
     * @param data
     *            Data to add
     */
    static byte[][] splitToHdlcFrames(final GXDLMSSettings settings,
            final int forFrame, final GXByteBuffer data) {
        int frameSize, len;
        int frame = forFrame;
        byte[] primaryAddress, secondaryAddress;
        if (settings.isServer()) {
            primaryAddress = getAddressBytes(settings.getClientAddress(), 0);
            secondaryAddress = getAddressBytes(settings.getServerAddress(),
                    settings.getServerAddressSize());
        } else {
            primaryAddress = getAddressBytes(settings.getServerAddress(),
                    settings.getServerAddressSize());
            secondaryAddress = getAddressBytes(settings.getClientAddress(), 0);
        }

        frameSize = GXCommon.intValue(settings.getLimits().getMaxInfoTX());
        // Remove header size.
        frameSize -= 7 + primaryAddress.length + secondaryAddress.length;
        List<byte[]> arr = new ArrayList<byte[]>();
        GXByteBuffer bb = new GXByteBuffer();
        // Add LLC bytes.
        byte[] llcBytes = null;
        int llcByteSize = 0;
        if ((frame & 1) == 0) {
            llcByteSize = 3;
            if (settings.isServer()) {
                llcBytes = GXCommon.LLC_REPLY_BYTES;
            } else {
                llcBytes = GXCommon.LLC_SEND_BYTES;
            }
        }
        do {
            // Add BOP
            bb.setUInt8(GXCommon.HDLC_FRAME_START_END);
            if (data == null || data.size() == 0) {
                // If no data
                bb.setUInt8(0xA0);
                // Skip data CRC from the total size.
                len = -2;
            } else if (data.size() - data.position()
                    + llcByteSize <= frameSize) {
                // Is last packet.
                bb.setUInt8(0xA0);
                len = data.size() - data.position();
            } else {
                // More data to left.
                bb.setUInt8(0xA8);
                len = frameSize;
            }
            // Add length.
            bb.setUInt8(7 + primaryAddress.length + secondaryAddress.length
                    + len + llcByteSize);
            // Add primary address.
            bb.set(primaryAddress);
            // Add secondary address.
            bb.set(secondaryAddress);
            // Generate frame if not generated yet.
            if (frame == 0) {
                frame = settings.getNextSend();
            }
            // Add frame.
            bb.setUInt8(frame);
            // Add header CRC.
            int crc = GXFCS16.countFCS16(bb.getData(), 1, bb.size() - 1);
            bb.setUInt16(crc);
            // Add LLC bytes only once.
            if (llcByteSize != 0) {
                bb.set(llcBytes);
                llcByteSize = 0;
            }
            // Add data.
            if (data != null && data.size() != 0) {
                bb.set(data.getData(), data.position(), len);
                data.position(data.position() + len);
                // Add data CRC.
                crc = GXFCS16.countFCS16(bb.getData(), 1, bb.size() - 1);
                bb.setUInt16(crc);
            }
            // Add EOP
            bb.setUInt8(GXCommon.HDLC_FRAME_START_END);
            arr.add(bb.array());
            bb.clear();
            frame = 0;
        } while (data != null && data.position() < data.size());
        byte[][] tmp = new byte[arr.size()][];
        int pos = -1;
        for (byte[] it : arr) {
            tmp[++pos] = it;
        }
        return tmp;
    }

    /**
     * Split DLMS PDU to wrapper frames.
     * 
     * @param settings
     *            DLMS settings.
     * @param data
     *            Wrapped data.
     */
    static byte[][] splitToWrapperFrames(final GXDLMSSettings settings,
            final GXByteBuffer data) {
        GXByteBuffer bb = new GXByteBuffer();
        // Add version.
        bb.setUInt16(1);
        if (settings.isServer()) {
            bb.setUInt16(settings.getServerAddress());
            bb.setUInt16(settings.getClientAddress());
        } else {
            bb.setUInt16(settings.getClientAddress());
            bb.setUInt16(settings.getServerAddress());
        }
        if (data == null) {
            // Data length.
            bb.setUInt16(0);
        } else {
            // Data length.
            bb.setUInt16(data.size());
            // Data
            bb.set(data.getData(), 0, data.size());
        }
        byte[][] tmp = new byte[1][];
        tmp[0] = bb.array();
        return tmp;

    }

    /**
     * Check LLC bytes.
     * 
     * @param server
     *            Is server.
     * @param data
     *            Received data.
     */
    private static void getLLCBytes(final boolean server,
            final GXByteBuffer data) {
        if (server) {
            data.compare(GXCommon.LLC_SEND_BYTES);
        } else {
            data.compare(GXCommon.LLC_REPLY_BYTES);
        }
    }

    static short getHdlcData(final boolean server,
            final GXDLMSSettings settings, final GXByteBuffer reply,
            final GXReplyData data) {
        short ch;
        int pos, packetStartID = reply.position(), frameLen = 0;
        int crc, crcRead;
        // If whole frame is not received yet.
        if (reply.size() - reply.position() < 9) {
            data.setComplete(false);
            return 0;
        }
        data.setComplete(true);
        // Find start of HDLC frame.
        for (pos = reply.position(); pos < reply.size(); ++pos) {
            ch = reply.getUInt8();
            if (ch == GXCommon.HDLC_FRAME_START_END) {
                packetStartID = pos;
                break;
            }
        }
        // Not a HDLC frame.
        if (reply.position() == reply.size()) {
            data.setComplete(false);
            reply.position(packetStartID);
            // Not enough data to parse;
            return 0;
        }
        data.setEcho(false);
        short frame = reply.getUInt8();
        // Check frame length.
        if ((frame & 0x7) != 0) {
            frameLen = ((frame & 0x7) << 8);
        }
        ch = reply.getUInt8();
        // If not enough data.
        frameLen += ch;
        if (reply.size() - reply.position() + 1 < frameLen) {
            data.setComplete(false);
            reply.position(packetStartID);
            // Not enough data to parse;
            return 0;

        }
        int eopPos = frameLen + packetStartID + 1;
        ch = reply.getUInt8(eopPos);
        if (ch != GXCommon.HDLC_FRAME_START_END) {
            throw new GXDLMSException("Invalid data format.");
        }

        // Check addresses.
        if (!checkHdlcAddress(server, settings, reply, data, eopPos)) {
            // If echo.
            data.setEcho(true);
            return getHdlcData(server, settings, reply, data);
        }
        // Is there more data available.
        if ((frame & 0x8) != 0) {
            data.setMoreData(RequestTypes.forValue(data.getMoreData().getValue()
                    | RequestTypes.FRAME.getValue()));
        } else {
            data.setMoreData(RequestTypes.forValue(data.getMoreData().getValue()
                    & ~RequestTypes.FRAME.getValue()));
        }
        // Get frame type.
        frame = reply.getUInt8();
        if (!settings.checkFrame(frame)) {
            throw new RuntimeException("Wrong frame index.");
        }
        FrameType type = FrameType.forValue(frame & 0xFF);
        // Check that header CRC is correct.
        crc = GXFCS16.countFCS16(reply.getData(), packetStartID + 1,
                reply.position() - packetStartID - 1);
        crcRead = reply.getUInt16();
        if (crc != crcRead) {
            throw new RuntimeException("Wrong CRC.");
        }
        // Check that packet CRC match only if there is a data part.
        if (reply.position() != packetStartID + frameLen + 1) {
            crc = GXFCS16.countFCS16(reply.getData(), packetStartID + 1,
                    frameLen - 2);
            crcRead = reply.getUInt16(packetStartID + frameLen - 1);
            if (crc != crcRead) {
                throw new RuntimeException("Wrong CRC.");
            }
        }
        if (type == FrameType.REJECTED) {
            reply.position(reply.size());
            data.setError((short) ErrorCode.REJECTED.getValue());
        } else if (type == FrameType.DISCONNECT_REQUEST) {
            // Get EOP.
            reply.getUInt8();
            data.setCommand(Command.DISCONNECT_REQUEST);
        } else if (type == FrameType.DISCONNECT_MODE) {
            // Get EOP.
            reply.getUInt8();
            data.setCommand(Command.DISCONNECT_REQUEST);
        } else if (type == FrameType.UA) {
            if (!settings.isServer()
                    && reply.position() == packetStartID + frameLen + 1) {
                // Get EOP.
                reply.getUInt8();
            }
            data.setCommand(Command.UA);
        } else if (type == FrameType.SNRM) {
            data.setCommand(Command.SNRM);
            // Get EOP.
            reply.getUInt8();
        } else {
            // If I frame.
            if ((frame & 1) == 0) {
                getLLCBytes(server, reply);
                if (type == FrameType.AARQ && !settings.getConnected()
                        && settings.isServer()) {
                    data.setCommand(Command.AARQ);
                }
            } else {
                // If U or S frame.
                // Get EOP.
                reply.getUInt8();
                data.setMoreData(RequestTypes.FRAME);
            }
        }
        // Skip data CRC and EOP.
        if (reply.position() != reply.size()) {
            reply.size(eopPos - 2);
        }
        return frame;
    }

    /**
     * Get physical and logical address from server address.
     * 
     * @param address
     *            Server address.
     * @param logical
     *            Logical address.
     * @param physical
     *            Physical address.
     */
    private static void getServerAddress(final int address, final int[] logical,
            final int[] physical) {
        if (address < 0x4000) {
            logical[0] = address >>> 7;
            physical[0] = address & 0x7F;
        } else {
            logical[0] = address >>> 14;
            physical[0] = address & 0x3FFF;
        }
    }

    private static boolean checkHdlcAddress(final boolean server,
            final GXDLMSSettings settings, final GXByteBuffer reply,
            final GXReplyData data, final int index) {
        int source, target;
        // Get destination and source addresses.
        target = GXCommon.getHDLCAddress(reply);
        source = GXCommon.getHDLCAddress(reply);
        if (server) {
            // Check that server addresses match.
            if (settings.getServerAddress() != 0
                    && settings.getServerAddress() != target) {
                throw new GXDLMSException(
                        "Destination addresses do not match. It is "
                                + String.valueOf(target) + ". It should be "
                                + String.valueOf(settings.getServerAddress())
                                + ".");
            } else {
                settings.setServerAddress(target);
            }

            // Check that client addresses match.
            if (settings.getClientAddress() != 0
                    && settings.getClientAddress() != source) {
                throw new GXDLMSException(
                        "Source addresses do not match. It is "
                                + String.valueOf(source) + ". It should be "
                                + String.valueOf(settings.getClientAddress())
                                + ".");
            } else {
                settings.setClientAddress(source);
            }
        } else {
            // Check that client addresses match.
            if (settings.getClientAddress() != target) {
                // If echo.
                if (settings.getClientAddress() == source
                        && settings.getServerAddress() == target) {
                    reply.position(index + 1);
                    return false;
                }
                throw new GXDLMSException(
                        "Destination addresses do not match. It is "
                                + String.valueOf(target) + ". It should be "
                                + String.valueOf(settings.getClientAddress())
                                + ".");
            }
            // Check that server addresses match.
            if (settings.getServerAddress() != source) {
                // Check logical and physical address separately.
                // This is done because some meters might send four bytes
                // when only two bytes is needed.
                int[] readLogical = new int[1], readPhysical = new int[1],
                        logical = new int[1], physical = new int[1];
                getServerAddress(source, readLogical, readPhysical);
                getServerAddress(settings.getServerAddress(), logical,
                        physical);
                if (readLogical[0] != logical[0]
                        || readPhysical[0] != physical[0]) {
                    throw new GXDLMSException(
                            "Source addresses do not match. It is "
                                    + String.valueOf(source) + ". It should be "
                                    + String.valueOf(
                                            settings.getServerAddress())
                                    + ".");
                }
            }
        }
        return true;
    }

    /**
     * Get data from TCP/IP frame.
     * 
     * @param settings
     *            DLMS settigns.
     * @param buff
     *            Received data.
     * @param data
     *            Reply information.
     */
    static void getTcpData(final GXDLMSSettings settings,
            final GXByteBuffer buff, final GXReplyData data) {
        // If whole frame is not received yet.
        if (buff.size() - buff.position() < 8) {
            data.setComplete(false);
            return;
        }
        int pos = buff.position();
        int value;
        // Get version
        value = buff.getUInt16();
        if (value != 1) {
            throw new GXDLMSException("Unknown version.");
        }

        // Check TCP/IP addresses.
        checkWrapperAddress(settings, buff, data);
        // Get length.
        value = buff.getUInt16();
        boolean compleate = !((buff.size() - buff.position()) < value);
        data.setComplete(compleate);
        if (!compleate) {
            buff.position(pos);
        }
    }

    private static void checkWrapperAddress(final GXDLMSSettings settings,
            final GXByteBuffer buff, final GXReplyData data) {
        int value;
        if (settings.isServer()) {
            value = buff.getUInt16();
            // Check that client addresses match.
            if (settings.getClientAddress() != 0
                    && settings.getClientAddress() != value) {
                throw new GXDLMSException(
                        "Source addresses do not match. It is "
                                + String.valueOf(value) + ". It should be "
                                + String.valueOf(settings.getClientAddress())
                                + ".");
            } else {
                settings.setClientAddress(value);
            }

            value = buff.getUInt16();
            // Check that server addresses match.
            if (settings.getServerAddress() != 0
                    && settings.getServerAddress() != value) {
                throw new GXDLMSException(
                        "Destination addresses do not match. It is "
                                + String.valueOf(value) + ". It should be "
                                + String.valueOf(settings.getServerAddress())
                                + ".");
            } else {
                settings.setServerAddress(value);
            }
        } else {
            value = buff.getUInt16();
            // Check that server addresses match.
            if (settings.getClientAddress() != 0
                    && settings.getServerAddress() != value) {
                throw new GXDLMSException(
                        "Source addresses do not match. It is "
                                + String.valueOf(value) + ". It should be "
                                + String.valueOf(settings.getServerAddress())
                                + ".");

            } else {
                settings.setServerAddress(value);
            }

            value = buff.getUInt16();
            // Check that client addresses match.
            if (settings.getClientAddress() != 0
                    && settings.getClientAddress() != value) {
                throw new GXDLMSException(
                        "Destination addresses do not match. It is "
                                + String.valueOf(value) + ". It should be "
                                + String.valueOf(settings.getClientAddress())
                                + ".");
            } else {
                settings.setClientAddress(value);
            }
        }
    }

    /**
     * Handle read response and get data from block and/or update error status.
     * 
     * @param data
     *            Received data from the client.
     */
    static boolean handleReadResponse(final GXReplyData data) {
        int pos = data.getData().position();
        if (GXCommon.getObjectCount(data.getData()) != 1) {
            data.getData().position(pos);
            return false;
        }
        short ch;
        // Get status code.
        ch = data.getData().getUInt8();
        if (ch == 0) {
            data.setError((byte) 0);
            getDataFromBlock(data.getData(), 0);
        } else {
            // Get error code.
            data.setError((byte) data.getData().getUInt8());
        }
        return true;
    }

    /**
     * Handle method response and get data from block and/or update error
     * status.
     * 
     * @param data
     *            Received data from the client.
     */
    static void handleMethodResponse(final GXDLMSSettings settings,
            final GXReplyData data) {
        short type;

        // Get type.
        type = data.getData().getUInt8();
        // Get invoke ID and priority.
        data.getData().getUInt8();
        byte ret = (byte) data.getData().getUInt8();
        if (ret != 0) {
            data.setError(ret);
        }
        if (type == 1) {
            // Response normal. Get data if exists.
            if (data.getData().position() < data.getData().size()) {
                int size = data.getData().getUInt8();
                if (size != 0) {
                    if (size != 1) {
                        throw new GXDLMSException(
                                "parseApplicationAssociationResponse failed. "
                                        + "Invalid tag.");
                    }
                    ret = (byte) data.getData().getUInt8();
                    if (ret != 0) {
                        throw new GXDLMSException(ret);
                    }
                    getDataFromBlock(data.getData(), 0);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid Command.");
        }
    }

    /**
     * Handle push and get data from block and/or update error status.
     * 
     * @param reply
     *            Received data from the client.
     */
    static void handlePush(final GXReplyData reply) {
        int index = reply.getData().position() - 1;
        // Is last block
        int last = reply.getData().getUInt8();
        if ((last & 0x80) == 0) {
            reply.setMoreData(
                    RequestTypes.forValue(reply.getMoreData().getValue()
                            | RequestTypes.DATABLOCK.getValue()));
        } else {
            reply.setMoreData(
                    RequestTypes.forValue(reply.getMoreData().getValue()
                            & ~RequestTypes.DATABLOCK.getValue()));

        }
        // Get block number sent.
        reply.getData().getUInt8();
        // Get block number acknowledged
        reply.getData().getUInt8();
        // Get APU tag.
        reply.getData().getUInt8();
        // Addl fields
        reply.getData().getUInt8();
        // Data-Notification
        if ((reply.getData().getUInt8() & 0x0F) == 0) {
            throw new RuntimeException("Invalid data.");
        }
        // Long-Invoke-Id-And-Priority
        reply.getData().getUInt32();
        // Get date time and skip it if used.
        int len = reply.getData().getUInt8();
        if (len != 0) {
            reply.getData().position(reply.getData().position() + len);
        }
        getDataFromBlock(reply.getData(), index);
    }

    /**
     * Handle set response and update error status.
     * 
     * @param reply
     *            Received data from the client.
     */
    static void handleSetResponse(final GXDLMSSettings settings,
            final GXReplyData data) {
    }

    /**
     * Handle write response and update error status.
     * 
     * @param reply
     *            Received data from the client.
     */
    static void handleWriteResponse(final GXDLMSSettings settings,
            final GXReplyData data) {
        int cnt = GXCommon.getObjectCount(data.getData());
        short ret;
        for (int pos = 0; pos != cnt; ++pos) {
            ret = data.getData().getUInt8();
            if (ret != 0) {
                ret = data.getData().getUInt8();
                data.setError(ret);
                throw new GXDLMSException(ret);
            }
        }
    }

    /**
     * Handle get response and get data from block and/or update error status.
     * 
     * @param settings
     *            DLMS settings.
     * @param reply
     *            Received data from the client.
     * @param index
     *            Block index number.
     */
    static boolean handleGetResponse(final GXDLMSSettings settings,
            final GXReplyData reply, final int index) {
        long number;
        short ch = 0, type;
        GXByteBuffer data = reply.getData();

        // Get type.
        type = data.getUInt8();
        // Get invoke ID and priority.
        ch = data.getUInt8();
        // Response normal
        if (type == 1) {
            // Result
            ch = data.getUInt8();
            if (ch != 0) {
                reply.setError((short) data.getUInt8());
            }
            getDataFromBlock(data, 0);
        } else if (type == 2) {
            // GetResponsewithDataBlock
            // Is Last block.
            ch = data.getUInt8();
            if (ch == 0) {
                reply.setMoreData(
                        RequestTypes.forValue(reply.getMoreData().getValue()
                                | RequestTypes.DATABLOCK.getValue()));
            } else {
                reply.setMoreData(
                        RequestTypes.forValue(reply.getMoreData().getValue()
                                & ~RequestTypes.DATABLOCK.getValue()));
            }
            // Get Block number.
            number = data.getUInt32();
            // If meter's block index is zero based.
            if (number == 0 && settings.getBlockIndex() == 1) {
                settings.setBlockIndex(0);
            }
            int expectedIndex = settings.getBlockIndex();
            if (number != expectedIndex) {
                throw new IllegalArgumentException(
                        "Invalid Block number. It is " + number
                                + " and it should be " + expectedIndex + ".");
            }
            // Get status.
            ch = data.getUInt8();
            if (ch != 0) {
                reply.setError(data.getUInt8());
            } else {
                // Get data size.
                reply.setBlockLength(GXCommon.getObjectCount(data));
                // if whole block is read.
                if ((reply.getMoreData().getValue()
                        & RequestTypes.FRAME.getValue()) == 0) {
                    // Check Block length.
                    if (reply.getBlockLength() > data.size()
                            - data.position()) {
                        throw new OutOfMemoryError();
                    }
                    reply.setCommand(Command.NONE);
                }
                getDataFromBlock(data, index);
                // If last packet and data is not try to peek.
                if (reply.getMoreData() == RequestTypes.NONE) {
                    if (!reply.getPeek()) {
                        data.position(0);
                        settings.resetBlockIndex();
                    }
                }
            }
        } else if (type == 3) {
            // Get response with list.
            getDataFromBlock(data, 0);
            return false;
        } else {
            throw new IllegalArgumentException("Invalid Get response.");
        }
        return true;
    }

    /**
     * Get PDU from the packet.
     * 
     * @param settings
     *            DLMS settings.
     * @param data
     *            received data.
     */
    public static void getPdu(final GXDLMSSettings settings,
            final GXReplyData data) {
        short ch;
        Command cmd = data.getCommand();
        if (data.getCommand() == Command.PUSH && (data.getMoreData().getValue()
                & RequestTypes.FRAME.getValue()) == 0) {
            data.setCommand(Command.NONE);
        }
        // If header is not read yet.
        if (data.getCommand() == Command.NONE) {
            int index = data.getData().position();
            // Get command.
            ch = data.getData().getUInt8();
            cmd = Command.forValue(ch);
            data.setCommand(cmd);
            switch (cmd) {
            case READ_RESPONSE:
                if (!handleReadResponse(data)) {
                    return;
                }
                break;
            case GET_RESPONSE:
                if (!handleGetResponse(settings, data, index)) {
                    return;
                }
                break;
            case SET_RESPONSE:
                handleSetResponse(settings, data);
                break;
            case WRITE_RESPONSE:
                handleWriteResponse(settings, data);
                break;
            case METHOD_RESPONSE:
                handleMethodResponse(settings, data);
                break;
            case PUSH:
                handlePush(data);
                break;
            case AARQ:
            case AARE:
                // This is parsed later.
                data.getData().position(data.getData().position() - 1);
                break;
            case DISCONNECT_RESPONSE:
                break;
            case EXCEPTION_RESPONSE:
                throw new GXDLMSException(
                        StateError.forValue(data.getData().getUInt8()),
                        ServiceError.forValue(data.getData().getUInt8()));
            case GET_REQUEST:
            case READ_REQUEST:
            case WRITE_REQUEST:
            case SET_REQUEST:
            case METHOD_REQUEST:
            case DISCONNECT_REQUEST:
                // Server handles this.
                break;
            case GLO_GET_REQUEST:
            case GLO_SET_REQUEST:
            case GLO_METHOD_REQUEST:
                if (settings.getCipher() == null) {
                    throw new RuntimeException(
                            "Secure connection is not supported.");
                }
                data.getData().position(data.getData().position() - 1);
                settings.getCipher().decrypt(settings.getSourceSystemTitle(),
                        data.getData());
                // Get command.
                ch = data.getData().getUInt8();
                cmd = Command.forValue(ch);
                data.setCommand(cmd);
                // Server handles this.
                break;
            case GLO_GET_RESPONSE:
            case GLO_SET_RESPONSE:
            case GLO_METHOD_RESPONSE:
                if (settings.getCipher() == null) {
                    throw new RuntimeException(
                            "Secure connection is not supported.");
                }
                data.getData().position(data.getData().position() - 1);
                settings.getCipher().decrypt(settings.getSourceSystemTitle(),
                        data.getData());
                data.setCommand(Command.NONE);
                getPdu(settings, data);
                break;
            case DATA_NOTIFICATION:
                // Get invoke id.
                ch = data.getData().getUInt8();
                // Client handles this.
                break;
            default:
                throw new IllegalArgumentException("Invalid Command.");
            }
        } else if ((data.getMoreData().getValue()
                & RequestTypes.FRAME.getValue()) == 0) {
            // Is whole block is read and if last packet and data is not try to
            // peek.
            if (!data.getPeek() && data.getMoreData() == RequestTypes.NONE) {
                data.getData().position(0);
                settings.resetBlockIndex();
            }
            // Get command if operating as a server.
            if (settings.isServer()) {
                data.getData().getUInt8();
            } else {
                // Client do not need a command any more.
                data.setCommand(Command.NONE);
            }
        }

        // Get data if all data is read or we want to peek data.
        if ((cmd == Command.READ_RESPONSE || cmd == Command.GET_RESPONSE)
                && (data.getMoreData() == RequestTypes.NONE
                        || data.getPeek())) {
            getValueFromData(settings, data);
        }
    }

    /**
     * Get value from data.
     * 
     * @param reply
     *            Received data.
     */
    static void getValueFromData(final GXDLMSSettings settings,
            final GXReplyData reply) {
        GXByteBuffer data = reply.getData();
        GXDataInfo info = new GXDataInfo();
        if (reply.getValue() instanceof Object[]) {
            info.setType(DataType.ARRAY);
            info.setCount(reply.getTotalCount());
            info.setIndex(reply.getCount());
        }
        int index = data.position();
        data.position(reply.getReadPosition());
        try {
            Object value = GXCommon.getData(data, info);
            if (value != null) { // If new data.
                if (!(value instanceof Object[])) {
                    reply.setValueType(info.getType());
                    reply.setValue(value);
                    reply.setTotalCount(0);
                } else {
                    if (((Object[]) value).length != 0) {
                        if (reply.getValue() == null) {
                            reply.setValue(value);
                        } else {
                            // Add items to collection.
                            List<Object> list = new ArrayList<Object>();
                            list.addAll(
                                    Arrays.asList((Object[]) reply.getValue()));
                            list.addAll(Arrays.asList((Object[]) value));
                            reply.setValue(list.toArray());
                        }
                    }
                    reply.setReadPosition(data.position());
                    // Element count.
                    reply.setTotalCount(info.getCount());
                }
            }
        } finally {
            data.position(index);
        }

        // If last data frame of the data block is read.
        if (reply.getMoreData() == RequestTypes.NONE) {
            // If all blocks are read.
            settings.resetBlockIndex();
            data.position(0);
        }
    }

    public static boolean getData(final GXDLMSSettings settings,
            final GXByteBuffer reply, final GXReplyData data) {
        short frame = 0;
        // If DLMS frame is generated.
        if (settings.getInterfaceType() == InterfaceType.HDLC) {
            frame = getHdlcData(settings.isServer(), settings, reply, data);
        } else if (settings.getInterfaceType() == InterfaceType.WRAPPER) {
            getTcpData(settings, reply, data);
        } else {
            throw new IllegalArgumentException("Invalid Interface type.");
        }
        // If all data is not read yet.
        if (!data.isComplete()) {
            return false;
        }

        getDataFromFrame(reply, data.getData());

        // If keepalive or get next frame request.
        if ((frame & 0x1) != 0) {
            return true;
        }

        // Get data.
        if (frame != FrameType.SNRM.getValue()
                && frame != FrameType.UA.getValue()
                && data.getCommand() != Command.AARQ
                && data.getCommand() != Command.AARE
                && frame != FrameType.DISCONNECT_MODE.getValue()) {
            getPdu(settings, data);
        }
        return true;
    }

    /**
     * Get data from HDLC or wrapper frame.
     * 
     * @param reply
     *            Received data that includes HDLC frame.
     * @param data
     *            Stored data.
     */
    private static void getDataFromFrame(final GXByteBuffer reply,
            final GXByteBuffer data) {
        int offset = data.size();
        int cnt = reply.size() - reply.position();
        if (cnt != 0) {
            data.capacity(offset + cnt);
            data.set(reply.getData(), reply.position(), cnt);
        }
        // Set position to begin of new data.
        data.position(offset);
    }

    /**
     * Get data from Block.
     * 
     * @param data
     *            Stored data block.
     * @param index
     *            Position where data starts.
     * @return Amount of removed bytes.
     */
    private static int getDataFromBlock(final GXByteBuffer data,
            final int index) {
        int len = data.position() - index;
        System.arraycopy(data.getData(), data.position(), data.getData(),
                data.position() - len, data.size() - data.position());
        data.size(data.size() - len);
        data.position(data.position() - len);
        return len;
    }

    /**
     * Returns action method information.
     * 
     * @param objectType
     *            Target object type.
     * @param value
     *            Starting address of action methods.
     * @param count
     *            Count of action methods.
     */
    static void getActionInfo(final ObjectType objectType, final int[] value,
            final int[] count) {
        switch (objectType) {
        case DATA:
        case ACTION_SCHEDULE:
        case ALL:
        case AUTO_ANSWER:
        case AUTO_CONNECT:
        case MAC_ADDRESS_SETUP:
        case GPRS_SETUP:
        case IEC_HDLC_SETUP:
        case IEC_LOCAL_PORT_SETUP:
        case IEC_TWISTED_PAIR_SETUP:
        case MODEM_CONFIGURATION:
        case PPP_SETUP:
        case REGISTER_MONITOR:
        case ZIG_BEE_SAS_STARTUP:
        case ZIG_BEE_SAS_JOIN:
        case ZIG_BEE_SAS_APS_FRAGMENTATION:
        case SCHEDULE:
        case SMTP_SETUP:
        case STATUS_MAPPING:
        case TCP_UDP_SETUP:
        case UTILITY_TABLES:
            throw new RuntimeException("Target do not support Action.");
        case IMAGE_TRANSFER:
            value[0] = 0x40;
            count[0] = 4;
            break;
        case ACTIVITY_CALENDAR:
            value[0] = 0x50;
            count[0] = 1;
            break;
        case ASSOCIATION_LOGICAL_NAME:
            value[0] = 0x60;
            count[0] = 4;
            break;
        case ASSOCIATION_SHORT_NAME:
            value[0] = 0x20;
            count[0] = 8;
            break;
        case CLOCK:
            value[0] = 0x60;
            count[0] = 6;
            break;
        case DEMAND_REGISTER:
            value[0] = 0x48;
            count[0] = 2;
            break;
        case EXTENDED_REGISTER:
            value[0] = 0x38;
            count[0] = 1;
            break;
        case IP4_SETUP:
            value[0] = 0x60;
            count[0] = 3;
            break;
        case MBUS_SLAVE_PORT_SETUP:
            value[0] = 0x60;
            count[0] = 8;
            break;
        case PROFILE_GENERIC:
            value[0] = 0x58;
            count[0] = 4;
            break;
        case REGISTER:
            value[0] = 0x28;
            count[0] = 1;
            break;
        case REGISTER_ACTIVATION:
            value[0] = 0x30;
            count[0] = 3;
            break;
        case REGISTER_TABLE:
            value[0] = 0x28;
            count[0] = 2;
            break;
        case SAP_ASSIGNMENT:
        case SCRIPT_TABLE:
            value[0] = 0x20;
            count[0] = 1;
            break;
        case SPECIAL_DAYS_TABLE:
            value[0] = 0x10;
            count[0] = 2;
            break;
        default:
            count[0] = 0;
            value[0] = 0;
            break;
        }
    }
}
