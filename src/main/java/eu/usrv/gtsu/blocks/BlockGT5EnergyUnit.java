package eu.usrv.gtsu.blocks;

import java.util.List;

import ic2.core.IC2;
import ic2.core.util.StackUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eu.usrv.gtsu.GTSUMod;
import eu.usrv.gtsu.multiblock.IMultiBlock;
import eu.usrv.gtsu.proxy.ClientProxy;
import eu.usrv.gtsu.tileentity.TEGT5EnergyInput;
import eu.usrv.gtsu.tileentity.TEGT5EnergyOutput;
import eu.usrv.gtsu.tileentity.TileEntityGTSU;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGT5EnergyUnit extends Block {
	@SideOnly(Side.CLIENT)
	protected IIcon icProducer;
	@SideOnly(Side.CLIENT)
	protected IIcon icAcceptor;

	public static final int ID_Acceptor = 0;
	public static final int ID_Producer = 1;
	
	public BlockGT5EnergyUnit() {
		super(Material.iron);

		setBlockName("GT5EnergyUnit");
		this.setCreativeTab(IC2.tabIC2);
		this.setHardness(3.0F);
		this.setStepSound(soundTypeMetal);
	}

	@Override
	public int damageDropped(int pMetadata)
	{
		return pMetadata;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister r)
	{
		icAcceptor= r.registerIcon("GTSU:powersystems/gt5_acceptor");
		icProducer = r.registerIcon("GTSU:powersystems/gt5_producer");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int pSide, int pMeta)
	{
		if (pMeta == ID_Acceptor)
			return icAcceptor;
		else if (pMeta == ID_Producer)
			return icProducer;

		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List stackList) 
	{
		stackList.add(new ItemStack(this, 1, 0));
		stackList.add(new ItemStack(this, 1, 1));
	}
	
	@Override
	public final boolean hasTileEntity(int metadata){
	    return true;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z){
	    return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockAccess world, int i, int j, int k){
	    return false;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side){
	    return true;
	}
    
	@Override
	public final TileEntity createTileEntity(World pWorld, int pMeta) {
		if (pMeta == ID_Acceptor)
			return new TEGT5EnergyInput();
		else if (pMeta == ID_Producer)
			return new TEGT5EnergyOutput(); 
		else
			return null;
	}
	
	@Override
	public boolean onBlockActivated(World pWorld, int x, int y, int z, EntityPlayer pPlayer, int par6, float par7, float par8, float par9) {
		int tCurrMeta = pWorld.getBlockMetadata(x, y, z);
		TileEntity te = pWorld.getTileEntity(x, y, z);
		if (te != null && te instanceof IMultiBlock)
			((IMultiBlock)te).onPlayerClicked(pPlayer);
		
		return true;
	}
}