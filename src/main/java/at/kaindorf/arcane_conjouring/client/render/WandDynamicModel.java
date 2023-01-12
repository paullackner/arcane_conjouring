package at.kaindorf.arcane_conjouring.client.render;

import com.google.common.collect.*;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Transformation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class WandDynamicModel implements IUnbakedGeometry<WandDynamicModel> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final BlockModel baseModel;
    private final boolean deprecatedLoader;

    public WandDynamicModel(BlockModel baseModel)
    {
        this(baseModel, false);
    }

    private WandDynamicModel(BlockModel baseModel, boolean deprecatedLoader)
    {
        this.baseModel = baseModel;
        this.deprecatedLoader = deprecatedLoader;
    }

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation)
    {
        if (deprecatedLoader)
            LOGGER.warn("Model \"" + modelLocation + "\" is using the deprecated loader \"forge:separate-perspective\" instead of \"forge:separate_transforms\". This loader will be removed in 1.20.");
        LOGGER.debug("bake wand model");
        var rootTransform = context.getRootTransform();
        if (!rootTransform.isIdentity())
            modelState = new SimpleModelState(modelState.getRotation().compose(rootTransform), modelState.isUvLocked());

        return new WandDynamicModel.Baked(
                context.useAmbientOcclusion(), context.isGui3d(), context.useBlockLight(),
                spriteGetter.apply(context.getMaterial("particle")), overrides,
                baseModel.bake(bakery, baseModel, spriteGetter, modelState, modelLocation, context.useBlockLight()),
                context.getTransforms()
        );
    }

    @Override
    public Collection<Material> getMaterials(IGeometryBakingContext context, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors)
    {
        Set<Material> textures = Sets.newHashSet();
        if (context.hasMaterial("particle"))
            textures.add(context.getMaterial("particle"));
        textures.addAll(baseModel.getMaterials(modelGetter, missingTextureErrors));
        return textures;
    }

    public static final class Baked implements IDynamicBakedModel{
        private final boolean isAmbientOcclusion;
        private final boolean isGui3d;
        private final boolean isSideLit;
        private final TextureAtlasSprite particle;
        private final ItemOverrides overrides;
        private final BakedModel baseModel;
        private final ItemTransforms transforms;

        public Baked(boolean isAmbientOcclusion, boolean isGui3d, boolean isSideLit, TextureAtlasSprite particle, ItemOverrides overrides, BakedModel baseModel, ItemTransforms transforms)
        {
            this.isAmbientOcclusion = isAmbientOcclusion;
            this.isGui3d = isGui3d;
            this.isSideLit = isSideLit;
            this.particle = particle;
            this.overrides = overrides;
            this.baseModel = baseModel;
            this.transforms = transforms;
        }

        @NotNull
        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType)
        {
            return baseModel.getQuads(state, side, rand, data, renderType);
        }

        @Override
        public boolean useAmbientOcclusion()
        {
            return isAmbientOcclusion;
        }

        @Override
        public boolean isGui3d()
        {
            return isGui3d;
        }

        @Override
        public boolean usesBlockLight()
        {
            return isSideLit;
        }

        @Override
        public boolean isCustomRenderer()
        {
            return true;
        }

        @Override
        public TextureAtlasSprite getParticleIcon()
        {
            return particle;
        }

        @Override
        public ItemOverrides getOverrides()
        {
            return overrides;
        }

        @Override
        public ItemTransforms getTransforms()
        {
            return transforms;
        }

        @Override
        public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data)
        {
            return baseModel.getRenderTypes(state, rand, data);
        }
    }

    public static final class Loader implements IGeometryLoader<WandDynamicModel>{
        public static final WandDynamicModel.Loader INSTANCE = new WandDynamicModel.Loader();

        public WandDynamicModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext)
        {
            JsonObject base = GsonHelper.getAsJsonObject(jsonObject, "base");

            BlockModel baseModel = deserializationContext.deserialize(base, BlockModel.class);

            JsonObject transforms = GsonHelper.getAsJsonObject(base, "display");


            JsonObject perspectiveData = GsonHelper.getAsJsonObject(jsonObject, "perspectives");



            Map<ItemTransforms.TransformType, BlockModel> perspectives = new HashMap<>();
            for (ItemTransforms.TransformType transform : ItemTransforms.TransformType.values())
            {
                if (perspectiveData.has(transform.getSerializeName()))
                {
                    BlockModel perspectiveModel = deserializationContext.deserialize(GsonHelper.getAsJsonObject(perspectiveData, transform.getSerializeName()), BlockModel.class);
                    perspectives.put(transform, perspectiveModel);
                }
            }

            return new WandDynamicModel(baseModel);
        }
    }
}