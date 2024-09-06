package com.watchtek.watchall.storage.smis.define;

public class StorageCIMDefine
{
    private StorageCIMDefine()
    {
        // SONAR
    }

    // CIM Class Define
    public static final String OPERATING_SYSTEM = "OperatingSystem";

    // CIM Property Define
    public static final String ELEMENT_NAME = "ElementName";

    // IBM CIM Classes
    public static final String[] IBM_STORWIZE_V5000_CIM_ARRAY = { "IBMTSSVC_Cluster", "IBMTSSVC_StorageSystemToDiskDrive", "IBMTSSVC_DeviceSAPImplementation", "IBMTSSVC_DiskDrive", "IBMTSSVC_HostedConcretePool", "IBMTSSVC_HostedPrimordialPool", "IBMTSSVC_ConcreteStoragePool",
        "IBMTSSVC_AllocatedFromConcretePool", "IBMTSSVC_StorageVolume", "IBMTSSVC_PrimordialStoragePool", "IBMTSSVC_AllocatedFromPrimordialPool", "IBMTSSVC_ComputerSystemPackage", "IBMTSSVC_Chassis", "IBMTSSVC_SystemVolumeController", "IBMTSSVC_ProtocolController",
        "IBMTSSVC_ProtocolControllerForPort", "IBMTSSVC_FCPort", "IBMTSSVC_SCSIProtocolEndpoint", "IBMTSSVC_HostedPerformanceStatisticsCollection", "IBMTSSVC_PerformanceStatisticsCollection", "IBMTSSVC_StorageVolumeStatsMemberOfCollection", "IBMTSSVC_StorageVolumeStatistics",
        "IBMTSSVC_AuthorizedControllerPrivilege", "IBMTSSVC_AuthorizedPrivilege", "IBMTSSVC_AuthorizedStorageHardwareID", "IBMTSSVC_StorageHardwareID", "IBMTSSVC_StorageVolumeStatisticalData", "IBMTSSVC_AllocatedFromPrimordialPool", "IBMTSSVC_VolumeBasedOnCompositeExtent",
        "IBMTSSVC_CompositeExtent", "IBMTSSVC_CompositeExtentBasedOn", "IBMTSSVC_BackendVolume", "IBMTSSVC_ArrayIsABackendVolume", "IBMTSSVC_Array", "IBMTSSVC_ArrayElementSettingData", "IBMTSSVC_RAIDSetting" };

    public static final String[] DELL_SC4020_CIM_ARRAY = { "CMPL_ComputerSystem", "CMPL_SystemDevice", "CMPL_DeviceSAPImplementation", "CMPL_DiskDrive", "CMPL_HostedStoragePool", "CMPL_AllocatedFromStoragePool", "CMPL_StoragePool", "CMPL_AllocatedFromStoragePool", "CMPL_StorageVolume",
        "CMPL_ComputerSystemPackage", "CMPL_PhysicalPackage", "CMPL_ProtocolControllerForUnit", "CMPL_SCSIProtocolController", "CMPL_ProtocolControllerForPort", "CMPL_FCPort", "CMPL_SCSIProtocolEndpoint", "CMPL_HostedCollection", "CMPL_StatisticsCollection", "CMPL_MemberOfCollection",
        "CMPL_BlockStorageStatisticalData", "CMPL_AuthorizedTarget", "CMPL_AuthorizedPrivilege", "CMPL_AuthorizedSubject", "CMPL_StorageHardwareID", "CMPL_ElementStatisticalData" };

    public static final String[] HITACHI_AMS2300_CIM_ARRAY = { "HITACHI_StorageSystem", "HITACHI_StorageSystemDeviceDiskDrive", "HITACHI_FCPortForSCSIProtocolEndpointImplementation", "HITACHI_DiskDrive", "HITACHI_HostedStoragePool", "HITACHI_HostedStoragePoolPrimordial", "HITACHI_StoragePool",
        "HITACHI_AllocatedFromStoragePool", "HITACHI_StoragePoolPrimordial", "HITACHI_AllocatedFromStoragePoolPrimordial", "HITACHI_StorageVolume", "HITACHI_StorageSystemPackageDKC", "HITACHI_DKCChassis", "HITACHI_SCSIPCForStorageVolume", "HITACHI_SCSIProtocolController", "HITACHI_SCSIPCForFCPort",
        "HITACHI_FCPort", "HITACHI_SCSIProtocolEndpoint", "HITACHI_HostedStatisticsCollection", "HITACHI_StatisticsCollection", "HITACHI_MemberOfStatisticsCollectionStorageVolume", "HITACHI_BlockStatisticalDataStorageVolume", "HITACHI_AuthorizedTarget", "HITACHI_AuthorizedPrivilege",
        "HITACHI_AuthorizedSubject", "HITACHI_StorageHardwareID", "HITACHI_ElementStatisticalDataStorageVolume", "HITACHI_AllocatedFromStoragePoolPrimordial", "HITACHI_AssociatedStoragePoolComponentArrayGroup", "HITACHI_ArrayGroup", "HITACHI_ComponentCS", "HITACHI_StorageProcessorSystem",
        "HITACHI_SoftwareIdentity", "HITACHI_HostedHiCommandAccessPoint", "HITACHI_HiCommandAccessPoint", "HITACHI_HiCommandAvailableForComputerSystem", "HITACHI_ComputerSystem", "HITACHI_InstalledSoftwareIdentity" };

    public static final String[] HITACHI_VSP_G200_CIM_ARRAY = { "HITACHI_StorageSystem", "HITACHI_StorageSystemDeviceDiskDrive", "HITACHI_FCPortForSCSIProtocolEndpointImplementation", "HITACHI_DiskDrive", "HITACHI_HostedStoragePool", "HITACHI_HostedStoragePoolPrimordial", "HITACHI_StoragePool",
        "HITACHI_AllocatedFromStoragePool", "HITACHI_StoragePoolPrimordial", "HITACHI_AllocatedFromStoragePoolPrimordial", "HITACHI_StorageVolume", "HITACHI_StorageSystemPackageDKC", "HITACHI_DKCChassis", "HITACHI_SCSIPCForStorageVolume", "HITACHI_SCSIProtocolController", "HITACHI_SCSIPCForFCPort",
        "HITACHI_FCPort", "HITACHI_SCSIProtocolEndpoint", "HITACHI_HostedStatisticsCollection", "HITACHI_StatisticsCollection", "HITACHI_MemberOfStatisticsCollectionStorageVolume", "HITACHI_BlockStatisticalDataStorageVolume", "HITACHI_AuthorizedTarget", "HITACHI_AuthorizedPrivilege",
        "HITACHI_AuthorizedSubject", "HITACHI_StorageHardwareID", "HITACHI_ElementStatisticalDataStorageVolume", "HITACHI_AllocatedFromStoragePoolPrimordial", "HITACHI_AssociatedStoragePoolComponentArrayGroup", "HITACHI_ArrayGroup", "HITACHI_ComponentCS", "HITACHI_StorageProcessorSystem",
        "HITACHI_SoftwareIdentity", "HITACHI_HostedHiCommandAccessPoint", "HITACHI_HiCommandAccessPoint", "HITACHI_HiCommandAvailableForComputerSystem", "HITACHI_ComputerSystem", "HITACHI_InstalledSoftwareIdentity" };

    public static final String[] HP_3PAR_CIM_ARRAY = { "TPD_VolumeAllocatedFromConcretePool", "TPD_AuthorizedPrivilege", "TPD_PrivilegeForStorageHardwareID", "TPD_PrivilegeForSCSIController", "TPD_VolumeStatisticalData", "TPD_StorageSystem", "TPD_ComputerSystemPackage",
        "TPD_FCPortSCSIEndpointImplementation", "TPD_DiskDrive", "TPD_VolumeElementStatisticalData", "TPD_FCPort", "TPD_HostedStatisticsCollection", "TPD_HostedStoragePool", "TPD_SystemPackage", "TPD_SCSIController", "TPD_ControllerForPort", "TPD_ControllerForUnit", "TPD_SCSIProtocolFCEndpoint",
        "TPD_StatisticsCollection", "TPD_StorageHardwareID", "TPD_StoragePool", "TPD_StorageVolume", "TPD_SystemDisk", "TPD_DynamicStoragePool", "TPD_VolumeAllocatedFromDynamicPool", };

    public static final String[] NETAPP_EF560_CIM_ARRAY = { "NETAPP_AllocatedFromStoragePool", "NETAPP_AuthorizedSubject", "NETAPP_AuthorizedTarget", "NETAPP_StorageVolumeStatisticalData", "NETAPP_StorageSystem", "NETAPP_ComputerSystemPackage", "NETAPP_DiskDrive", "NETAPP_ElementStatisticalData",
        "NETAPP_HostedStoragePool", "NETAPP_HostedStoragePool", "NETAPP_IsSpare", "NETAPP_Enclosure", "NETAPP_SCSIProtocolController", "NETAPP_LUNMapping", "NETAPP_SCSIProtocolEndpoint", "NETAPP_HostInitiatorPort", "NETAPP_StoragePool", "NETAPP_ParentStoragePool", "NETAPP_StorageVolume",
        "NETAPP_StorageSystemDevice", "NETAPP_DiskExtent", "NETAPP_MediaPresent", "NETAPP_ControllerFirmwareIdentity", "NETAPP_ElementSoftwareIdentity", "NETAPP_Host" };
}
